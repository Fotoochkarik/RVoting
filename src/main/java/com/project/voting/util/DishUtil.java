package com.project.voting.util;

import com.project.voting.model.Dish;
import com.project.voting.to.DishTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(), dishTo.getPrice());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
        return dish;
    }

    public static DishTo convertToDishTo(Dish dish){
        return new DishTo(null, dish.getName(), dish.getPrice());
    }
}