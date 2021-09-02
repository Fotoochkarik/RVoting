package com.project.voting.web.restaurant;

import com.project.voting.repository.RestaurantRepository;
import com.project.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.project.voting.web.RestaurantTestData.*;
import static com.project.voting.web.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + "/";

    @Autowired
    private RestaurantRepository repository;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MACDONALDS_ID + "/with-dishes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(WITH_DISHES_MATCHER.contentJson(macdonalds));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + KFC_ID + "/with-votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(WITH_VOTES_MATCHER.contentJson(kfc));
    }

//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void getAllWithVotes() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL + "/with-votes"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(WITH_VOTES_MATCHER.contentJson(macdonalds, kfc, burgerKing));
//    }
//
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void getAll() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
////                .andExpect(WITH_DISHES_MATCHER.contentJson(macdonalds, kfc, burgerKing));
//
//                WITH_DISHES_MATCHER.assertMatch(repository.findAllWithDishes(), macdonalds, kfc, burgerKing);
//    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by")
                .param("date", "2021-08-30"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(macdonalds, burgerKing));
    }
}