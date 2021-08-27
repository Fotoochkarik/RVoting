package com.project.voting.web;

import com.project.voting.model.Restaurant;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "registered");

    public static final int MACDONALDS_ID = 1;
    public static final int KFC_ID = 2;
    public static final int BURGER_KING_ID = 3;
    public static final int NOT_FOUND = 100;

    public static final Restaurant macdonalds = new Restaurant(MACDONALDS_ID, "Macdonald`s");
    public static final Restaurant kfc = new Restaurant(KFC_ID, "KFC");
    public static final Restaurant burgerKing = new Restaurant(BURGER_KING_ID, "Burger King");

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(MACDONALDS_ID, "UpdatedName");
    }
}
