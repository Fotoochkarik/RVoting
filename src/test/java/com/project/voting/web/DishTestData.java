package com.project.voting.web;

import com.project.voting.model.Dish;

import java.time.LocalDate;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);

    public static final int BURGER_ID = 1;
    public static final int BIG_BURGER_ID = 2;
    public static final int DISH1_ID = 3;
    public static final int DISH2_ID = 4;
    public static final int DISH3_ID = 5;
    public static final int DISH4_ID = 6;
    public static final int DISH5_ID = 7;
    public static final int DISH6_ID = 8;
    public static final int DISH7_ID = 9;
    public static final int DISH8_ID = 10;
    public static final int DISH9_ID = 11;
    public static final int DISH10_ID = 12;
    public static final int DISH11_ID = 13;
    public static final int DISH12_ID = 14;
    public static final int DISH13_ID = 15;
    public static final int DISH14_ID = 16;
    public static final int DISH15_ID = 17;
    public static final int DISH16_ID = 18;
    public static final int DISH17_ID = 19;
    public static final int DISH18_ID = 20;
    public static final int DISH19_ID = 21;
    public static final int DISH20_ID = 22;
    public static final int DISH21_ID = 23;
    public static final int NOT_FOUND = 100;

    public static final Dish burger = new Dish(BURGER_ID, LocalDate.of(2021, 8, 30), "Burger", 123);
    public static final Dish bigBurger = new Dish(BIG_BURGER_ID, LocalDate.of(2021, 8, 30), "Big Burger", 100);
    public static final Dish dish1 = new Dish(DISH1_ID, LocalDate.of(2021, 8, 29), "Dish1", 100);
    public static final Dish dish2 = new Dish(DISH2_ID, LocalDate.of(2021, 8, 29), "Dish2", 101);
    public static final Dish dish3 = new Dish(DISH3_ID, LocalDate.of(2021, 8, 29), "Dish3", 102);
    public static final Dish dish4 = new Dish(DISH4_ID, LocalDate.of(2021, 8, 30), "Dish4", 103);
    public static final Dish dish5 = new Dish(DISH5_ID, LocalDate.of(2021, 8, 30), "Dish5", 104);
    public static final Dish dish6 = new Dish(DISH6_ID, LocalDate.of(2021, 8, 30), "Dish6", 105);
    public static final Dish dish7 = new Dish(DISH7_ID, "Dish7", 106);
    public static final Dish dish8 = new Dish(DISH8_ID, "Dish8", 107);
    public static final Dish dish9 = new Dish(DISH9_ID, "Dish9", 108);
    public static final Dish dish10 = new Dish(DISH10_ID, "Dish10", 109);
    public static final Dish dish11 = new Dish(DISH11_ID, "Dish11", 110);
    public static final Dish dish12 = new Dish(DISH12_ID, "Dish12", 1018);
    public static final Dish dish13 = new Dish(DISH13_ID, "Dish13", 111);
    public static final Dish dish14 = new Dish(DISH14_ID, "Dish14", 1123);
    public static final Dish dish15 = new Dish(DISH15_ID, "Dish15", 112);
    public static final Dish dish16 = new Dish(DISH16_ID, "Dish16", 113);
    public static final Dish dish17 = new Dish(DISH17_ID, "Dish17", 115);
    public static final Dish dish18 = new Dish(DISH18_ID, "Dish18", 116);
    public static final Dish dish19 = new Dish(DISH19_ID, "Dish19", 117);
    public static final Dish dish20 = new Dish(DISH20_ID, "Dish20", 118);
    public static final Dish dish21 = new Dish(DISH21_ID, "Dish21", 119);

    public static Dish getNew() {
        return new Dish(null, "New", 150);
    }

    public static Dish getUpdated() {
        return new Dish(BURGER_ID, "UpdatedName", 321);
    }
}
