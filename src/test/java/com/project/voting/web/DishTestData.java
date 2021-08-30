package com.project.voting.web;

import com.project.voting.model.Dish;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);

    public static final int BREAKFAST_ID = 1;
    public static final int LUNCH_ID = 2;
    public static final int NOT_FOUND = 100;
    public static final Dish breakfast = new Dish(BREAKFAST_ID, "Breakfast", 123);
    public static final Dish lunch = new Dish(LUNCH_ID, "Lunch", 100);

    public static Dish getNew() {
        return new Dish(null, "New", 150);
    }

    public static Dish getUpdated() {
        return new Dish(BREAKFAST_ID, "UpdatedName", 321);
    }
}
