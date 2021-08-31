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

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        log.info("getAll for user {}", authUser.id());
        return repository.getAll(authUser.id());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get {} vote for user {}", id, authUser.id());
        return ResponseEntity.of(repository.findByIdAndUserId(id, authUser.id()));
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
        LocalDateTime current = LocalDateTime.now();
        Vote newVote;
        Optional<Vote> vote = repository.findByUserIdAndDate(authUser.id(), current.toLocalDate());
        if (vote.isEmpty()) {
            newVote = new Vote(null, userRepository.getById(authUser.id()), restaurantRepository.getById(restaurantId));
            log.info("create {} vote for restaurant {}", newVote.getDateCreation(), restaurantId);
            checkNew(newVote);
        } else {
            if (current.toLocalTime().isBefore(vote.get().getLIMIT_TIME_OF_VOTING())) {
                log.info("update restaurant {} for vote  {}", restaurantId, vote);
                newVote = vote.get();
                newVote.setRestaurant(restaurantRepository.getById(restaurantId));
            } else {
                throw new IllegalArgumentException("Time exceeded for voting " + vote.get().getLIMIT_TIME_OF_VOTING());
            }
        }
        Vote created = repository.save(newVote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
