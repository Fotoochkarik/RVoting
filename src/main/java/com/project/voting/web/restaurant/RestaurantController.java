package com.project.voting.web.restaurant;

import com.project.voting.model.Restaurant;
import com.project.voting.repository.DishRepository;
import com.project.voting.repository.RestaurantRepository;
import com.project.voting.to.DishTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.project.voting.util.DishUtil.convertToDishTo;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private DishRepository dishRepository;

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        log.info("getAll with dishes");
        return repository.findAllWithDishes();
    }

    @GetMapping("/{id}/with-dishes")
    public Restaurant getWithDishes(@PathVariable int id) {
        log.info("get {} with dishes", id);
        return repository.getWithDishes(id);
    }

    @GetMapping("/{id}/with-votes")
    public Restaurant getWithVotes(@PathVariable int id) {
        log.info("get {} with votes", id);
        return repository.getWithVotes(id);
    }

    @GetMapping("/{id}/menu/by")
    public List<DishTo> getMenuByDate(@PathVariable int id,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return convertToDishTo(dishRepository.getMenuByDateOfUse(id, date));
    }

    @GetMapping("/{id}/menu")
    public List<DishTo> getCurrentMenu(@PathVariable int id) {
        return convertToDishTo(dishRepository.getMenuByDateOfUse(id, LocalDate.now()));
    }
}
