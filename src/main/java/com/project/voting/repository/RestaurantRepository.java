package com.project.voting.repository;

import com.project.voting.model.Restaurant;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}
