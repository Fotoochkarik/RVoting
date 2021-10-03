package com.project.voting.web.vote;

import com.project.voting.error.IllegalRequestDataException;
import com.project.voting.error.NotFoundException;
import com.project.voting.model.Vote;
import com.project.voting.repository.RestaurantRepository;
import com.project.voting.repository.UserRepository;
import com.project.voting.repository.VoteRepository;
import com.project.voting.util.TimeUtil;
import com.project.voting.web.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.project.voting.util.TimeUtil.LIMIT_TIME_OF_VOTING;
import static com.project.voting.util.validation.ValidationUtil.assureIdConsistent;

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
    public ResponseEntity<Vote> get(@AuthenticationPrincipal AuthUser authUser,
                                    @PathVariable int id) {
        log.info("get {} vote for user {}", id, authUser.id());
        return ResponseEntity.of(repository.findByIdAndUserId(id, authUser.id()));
    }

    @PostMapping(value = "vote/{restaurantId}")
    @Transactional
    public ResponseEntity<Vote> create(@AuthenticationPrincipal AuthUser authUser,
                                       @PathVariable int restaurantId) {
        Optional<Vote> vote = getForToday(authUser.id());
        if (vote.isEmpty()) {
            vote = Optional.of(new Vote(null, userRepository.findById(authUser.id()).orElseThrow(() -> new NotFoundException("Not found " + authUser.id())),
                    restaurantRepository.findById(restaurantId).orElseThrow(() -> new IllegalRequestDataException("Not found " + restaurantId))));
            log.info("create {} vote for restaurant {}", vote, restaurantId);
        }
        Vote created = repository.save(vote.get());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void change(@AuthenticationPrincipal AuthUser authUser,
                       @RequestBody Vote vote,
                       @PathVariable int id) {
        Assert.notNull(vote, "Vote must not be null");
        assureIdConsistent(vote, id);
        if (getForToday(authUser.id()).isPresent()) {
            if (LocalTime.now(TimeUtil.clock).isBefore(LIMIT_TIME_OF_VOTING)) {
                log.info("update restaurant {} for vote  {}", vote.getRestaurant(), vote);
                repository.save(vote);
            } else {
                throw new IllegalRequestDataException("Time exceeded for voting " + LIMIT_TIME_OF_VOTING);
            }
        }
    }

    private Optional<Vote> getForToday(int userId) {
        return repository.findByUserIdAndDate(userId, LocalDate.now(TimeUtil.clock));
    }
}
