package com.project.voting.web;

import com.project.voting.model.Vote;
import com.project.voting.repository.VoteRepository;
import com.project.voting.util.JsonUtil;
import com.project.voting.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static com.project.voting.TestUtil.userHttpBasic;
import static com.project.voting.web.RestaurantTestData.KFC_ID;
import static com.project.voting.web.RestaurantTestData.MACDONALDS_ID;
import static com.project.voting.web.UserTestData.ADMIN_MAIL;
import static com.project.voting.web.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteController.REST_URL + "/";

    @Autowired
    private VoteRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(adminVote, adminVote2));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_VOTE_ID)
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(adminVote));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + ADMIN_VOTE_ID)
                .with(userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(ADMIN_VOTE_ID).isPresent());
    }

    @Test
    void deleteWithoutAccess() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + ADMIN_VOTE_ID))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND)
                .with(userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createNewVote() throws Exception {
//        https://www.baeldung.com/java-override-system-time#mocking-the-localdatetimenow-method
        TimeUtil.clock = Clock.fixed(Instant.parse("2021-08-31T12:15:30.00Z"), ZoneId.of("UTC"));
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(KFC_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.user))
                .content(JsonUtil.writeValue(newVote)))
                .andDo(print());

        Vote created = MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        MATCHER.assertMatch(created, newVote);
        MATCHER.assertMatch(repository.getById(newId), newVote);
    }

    @Test
    void changeVoteBeforeLimitedTime() throws Exception {
//        https://www.baeldung.com/java-override-system-time#mocking-the-localdatetimenow-method
        TimeUtil.clock = Clock.fixed(Instant.parse("2021-08-30T10:40:59.00Z"), ZoneId.of("UTC"));
        Vote updated = getUpdated();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(MACDONALDS_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.admin))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isCreated());

        MATCHER.assertMatch(repository.getById(ADMIN_VOTE_ID), getUpdated());
    }

    @Test
    void changeVoteAfterLimitedTime() throws Exception {
//        https://www.baeldung.com/java-override-system-time#mocking-the-localdatetimenow-method
        TimeUtil.clock = Clock.fixed(Instant.parse("2021-08-30T12:40:59.00Z"), ZoneId.of("UTC"));
        Vote updated = getUpdated();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(MACDONALDS_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.admin))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}