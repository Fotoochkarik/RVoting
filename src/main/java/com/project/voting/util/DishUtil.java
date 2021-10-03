package com.project.voting.util;

import com.project.voting.model.Dish;
import com.project.voting.to.DishTo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getDateOfUse(), dishTo.getName(), dishTo.getPrice());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
        return dish;
    }

    public static DishTo convertToDishTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getDateOfUse(), dish.getName(), dish.getPrice());
    }

    public static List<DishTo> convertToDishTo(List<Dish> dishList) {
        return dishList.stream()
                .map(DishUtil::convertToDishTo)
                .collect(Collectors.toList());
    }
}
