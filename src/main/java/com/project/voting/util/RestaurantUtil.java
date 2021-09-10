package com.project.voting.util;

import com.project.voting.model.Restaurant;
import com.project.voting.to.RestaurantTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName());
    }

    public static Restaurant convertFromTo(RestaurantTo restaurantTo) {

        return new Restaurant(restaurantTo.getId(), restaurantTo.getName());
    }
}
