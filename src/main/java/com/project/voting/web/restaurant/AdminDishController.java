package com.project.voting.web.restaurant;

import com.project.voting.error.NotFoundException;
import com.project.voting.model.Dish;
import com.project.voting.repository.DishRepository;
import com.project.voting.repository.RestaurantRepository;
import com.project.voting.to.DishTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminDishController {

    static final String REST_URL = "/api/admin/restaurants/";

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("{restaurantId}/dishes/{id}")
    public ResponseEntity<Dish> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(dishRepository.findByIdAndRestaurantId(id, restaurantId));
    }

    @GetMapping("{restaurantId}/dishes")
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return dishRepository.getAll(restaurantId);
    }

    @DeleteMapping("/{restaurantId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "restaurantId") int restaurantId, @PathVariable int id) {
        log.info("delete {} from restaurant {}", id, restaurantId);
        dishRepository.deleteExisted(id);
    }

    @PostMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Dish> create(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurantId) {
        checkNew(dishTo);
        Dish dish = createNewFromTo(dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        log.info("create {} for restaurant {}", dish, restaurantRepository.findById(restaurantId));
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/dishes")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update {} with id={} for restaurant {}", dishTo, id, restaurantId);
        assureIdConsistent(dishTo, id);
        Dish dish = dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
        updateFromTo(dish, dishTo);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        dishRepository.save(dish);
    }
}
