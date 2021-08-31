package com.project.voting.web;

import com.project.voting.model.Vote;
import com.project.voting.repository.VoteRepository;
import com.project.voting.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.project.voting.TestUtil.userHttpBasic;
import static com.project.voting.web.RestaurantTestData.KFC_ID;
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
    void create() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(KFC_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(UserTestData.user))
                .content(JsonUtil.writeValue(newVote)));

        Vote created = MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        MATCHER.assertMatch(created, newVote);
        MATCHER.assertMatch(repository.getById(newId), newVote);
    }
}