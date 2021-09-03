package com.project.voting.web;

import com.project.voting.model.Vote;

import java.time.LocalDate;

import static com.project.voting.web.RestaurantTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");
    public static final int USER_VOTE_ID = 1;
    public static final int ADMIN_VOTE_ID = 2;
    public static final int NOT_FOUND = 100;

    public static final Vote userVote = new Vote(USER_VOTE_ID, UserTestData.user, macdonalds, LocalDate.of(2021, 8, 30));
    public static final Vote adminVote = new Vote(ADMIN_VOTE_ID, UserTestData.admin, macdonalds, LocalDate.of(2021, 8, 30));
    public static final Vote adminVote2 = new Vote(ADMIN_VOTE_ID + 1, UserTestData.admin, burgerKing, LocalDate.of(2021, 8, 31));

    public static Vote getNew() {
        return new Vote(null, UserTestData.user, kfc);
    }

    public static Vote getUpdated() {
        return new Vote(ADMIN_VOTE_ID, UserTestData.admin, macdonalds, LocalDate.of(2021, 8, 30));
    }
}
