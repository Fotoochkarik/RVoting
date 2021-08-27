package com.project.voting.web.user;

import com.project.voting.model.Dish;
import com.project.voting.repository.DishRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.project.voting.util.validation.ValidationUtil.assureIdConsistent;
import static com.project.voting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
// TODO: cache only most requested data!
@CacheConfig(cacheNames = "dishes")
public class AdminDishController {

    static final String REST_URL = "/api/admin/dishes";

    @Autowired
    private DishRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id) {
        log.info("get {} dish", id);
        return ResponseEntity.of(repository.findById(id)
//                .filter(dish -> restaurantId == dish.getRestaurant().getId())
        );
    }

    @GetMapping
    public List<Dish> getAll(){
        log.info("getAll");
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish) {
        log.info("create {}", dish);
        checkNew(dish);
        Dish created = repository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        repository.save(dish);
    }
}
