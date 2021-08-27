package com.project.voting.repository;

import com.project.voting.model.Dish;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

//    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId")
//    List<Dish> getAll(@Param("restaurantId") int restaurantId);
//
//    @Query("SELECT d FROM Dish d WHERE d.id = ?1 AND d.restaurant.id = ?2")
//    Dish getWithRestaurant(int id, int restaurantId);
}
