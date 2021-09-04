package com.project.voting.web.restaurant;

import com.project.voting.error.NotFoundException;
import com.project.voting.model.Dish;
import com.project.voting.model.Restaurant;
import com.project.voting.repository.DishRepository;
import com.project.voting.repository.RestaurantRepository;
import com.project.voting.to.DishTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.project.voting.util.DishUtil.createNewFromTo;
import static com.project.voting.util.DishUtil.updateFromTo;
import static com.project.voting.util.validation.ValidationUtil.assureIdConsistent;
import static com.project.voting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "dishes")
public class AdminDishController {

    static final String REST_URL = "/api/admin/restaurant/";

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("{restaurant}/{id}")
    public ResponseEntity<Dish> get(@PathVariable Restaurant restaurant, @PathVariable int id) {
        log.info("get {} for restaurant {}", id, restaurant);
        return ResponseEntity.of(dishRepository.findByIdAndRestaurantId(id, restaurant.id()));
    }

    @GetMapping("{id}/dishes")
    @Cacheable
    public List<Dish> getAll(@PathVariable int id) {
        log.info("getAll for restaurant {}", id);
        return dishRepository.getAll(id);
    }

    @DeleteMapping("/{restaurant}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Restaurant restaurant, @PathVariable int id) {
        log.info("delete {} from restaurant {}", id, restaurant);
        dishRepository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Dish> create(@Valid @RequestBody DishTo dishTo, @RequestParam("restaurantId") int restaurantId) {
        checkNew(dishTo);
        Dish dish = createNewFromTo(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        log.info("create {} for restaurant {}", dish, restaurantRepository.findById(restaurantId));
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurant}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id={} for restaurant {}", dishTo, id, restaurant);
        assureIdConsistent(dishTo, id);
        Dish dish = dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
        updateFromTo(dish, dishTo);
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);
    }
}
