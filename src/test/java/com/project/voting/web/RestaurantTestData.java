package com.project.voting.web;

import com.project.voting.model.Restaurant;

import java.util.List;

import static com.project.voting.web.DishTestData.breakfast;
import static com.project.voting.web.DishTestData.lunch;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "registered", "menu");
    public static MatcherFactory.Matcher<Restaurant> WITH_DISHES_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("registered", "menu.restaurant", "votes").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int MACDONALDS_ID = 1;
    public static final int KFC_ID = 2;
    public static final int BURGER_KING_ID = 3;
    public static final int NOT_FOUND = 100;

    public static final Restaurant macdonalds = new Restaurant(MACDONALDS_ID, "Macdonald`s");
    public static final Restaurant kfc = new Restaurant(KFC_ID, "KFC");
    public static final Restaurant burgerKing = new Restaurant(BURGER_KING_ID, "Burger King");

    static {
        macdonalds.setMenu(List.of(breakfast, lunch));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(MACDONALDS_ID, "UpdatedName");
    }
}
