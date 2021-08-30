package com.project.voting.web;

import com.project.voting.model.Vote;
import com.project.voting.repository.RestaurantRepository;
import com.project.voting.repository.UserRepository;
import com.project.voting.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.project.voting.util.validation.ValidationUtil.assureIdConsistent;
import static com.project.voting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/profile/votes";

    @Autowired
    private VoteRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping
    public List<Vote> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll");
        return repository.getAll(authUser.id());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@PathVariable int id) {
        log.info("get {} vote", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {} vote", id);
        repository.deleteExisted(id);
    }

    @PostMapping
    public ResponseEntity<Vote> create(
//            @ApiIgnore
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam("restaurantId") int restaurantId
    ) {
        Vote vote = new Vote(null, userRepository.getById(authUser.id()), restaurantRepository.getById(restaurantId));
        log.info("create {} vote", vote);
        checkNew(vote);
        Vote created = repository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Vote vote, @PathVariable int id) {
        log.info("update {} with id={}", vote, id);
        assureIdConsistent(vote, id);
        repository.save(vote);
    }

    @GetMapping("/getAll")
    public List<Vote> getAllWithRestaurant(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll with restaurants");
        return repository.getAllWithRestaurant(authUser.id());
    }
}
