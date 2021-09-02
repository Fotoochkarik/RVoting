package com.project.voting.web.restaurant;

import com.project.voting.model.Restaurant;
import com.project.voting.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    @Autowired
    private RestaurantRepository repository;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll with dishes");
        return repository.findAllWithDishes();
    }

    @GetMapping("/{id}/with-dishes")
    public Restaurant getWithDishes(
//            @ApiIgnore
            @PathVariable int id
    ) {
        log.info("get {} with dishes", id);
        return repository.getWithDishes(id);
    }

    @GetMapping("/{id}/with-votes")
    public Restaurant getWithVotes(
//            @ApiIgnore
            @PathVariable int id
    ) {
        log.info("get {} with votes", id);
        return repository.getWithVotes(id);
    }

    @GetMapping("/with-votes")
    public List<Restaurant> getAllWithVotes() {
        log.info("getAll with votes");
        return repository.findAllWithVotes();
    }

    @GetMapping("/by")
    public List<Restaurant> getByDate(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get by date {}", date);
        return repository.findByDateCreation(date);
    }
}
