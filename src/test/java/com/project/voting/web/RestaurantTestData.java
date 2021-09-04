package com.project.voting.web;

import com.project.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static com.project.voting.web.DishTestData.*;
import static com.project.voting.web.VoteTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dateCreation", "menu", "votes");

    public static final MatcherFactory.Matcher<Restaurant> WITH_DISHES_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("dateCreation", "menu", "votes").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final MatcherFactory.Matcher<Restaurant> WITH_VOTES_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("dateCreation", "menu", "votes").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int MACDONALDS_ID = 1;
    public static final int KFC_ID = 2;
    public static final int BURGER_KING_ID = 3;
    public static final int BURGER_LAB_ID = 4;
    public static final int HESBURGER_ID = 5;
    public static final int CARLS_JR_ID = 6;
    public static final int BURGER_LAB_NOW_ID = 7;
    public static final int NOT_FOUND = 100;

    public static final Restaurant macdonalds = new Restaurant(MACDONALDS_ID, "Macdonald`s", LocalDate.of(2021, 8, 30));
    public static final Restaurant kfc = new Restaurant(KFC_ID, "KFC", LocalDate.of(2021, 8, 29));
    public static final Restaurant burgerKing = new Restaurant(BURGER_KING_ID, "Burger King", LocalDate.of(2021, 8, 30));
    public static final Restaurant burgerLab = new Restaurant(BURGER_LAB_ID, "Burger Lab", LocalDate.of(2021, 8, 30));
    public static final Restaurant hesburger = new Restaurant(HESBURGER_ID, "Hesburger");
    public static final Restaurant CarlsJr = new Restaurant(CARLS_JR_ID, "Carl’s Jr.");
    public static final Restaurant BlackStarBurger = new Restaurant(BURGER_LAB_NOW_ID, "Black Star Burger");

    public static final List<Restaurant> restaurants = List.of(macdonalds, kfc, burgerKing, burgerLab, hesburger, CarlsJr, BlackStarBurger);

    static {
        macdonalds.setMenu(List.of(burger, bigBurger));
        kfc.setMenu(List.of(dish1, dish2, dish3));
        burgerKing.setMenu(List.of(dish4, dish5, dish6));
        burgerLab.setMenu(List.of(dish7, dish8, dish9, dish10));
        hesburger.setMenu(List.of(dish11, dish12, dish13, dish14));
        CarlsJr.setMenu(List.of(dish16, dish17, dish18, dish15));
        BlackStarBurger.setMenu(List.of(dish19, dish20, dish21));
        macdonalds.setVotes(List.of(userVote, adminVote));
        kfc.setVotes(List.of(adminVote));
        burgerKing.setVotes(List.of(adminVote2));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant", LocalDate.of(2021, 8, 30));
    }

    public static Restaurant getUpdated() {
        return new Restaurant(MACDONALDS_ID, "UpdatedName");
    }
}
