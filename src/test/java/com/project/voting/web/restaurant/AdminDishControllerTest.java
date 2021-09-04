package com.project.voting.web.restaurant;

import com.project.voting.model.Dish;
import com.project.voting.repository.DishRepository;
import com.project.voting.to.DishTo;
import com.project.voting.util.JsonUtil;
import com.project.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.project.voting.util.DishUtil.convertToDishTo;
import static com.project.voting.util.DishUtil.createNewFromTo;
import static com.project.voting.web.DishTestData.*;
import static com.project.voting.web.RestaurantTestData.KFC_ID;
import static com.project.voting.web.RestaurantTestData.MACDONALDS_ID;
import static com.project.voting.web.UserTestData.ADMIN_MAIL;
import static com.project.voting.web.UserTestData.USER_MAIL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.REST_URL + "/";

    @Autowired
    private DishRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MACDONALDS_ID + "/" + BURGER_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(burger));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotExistForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + KFC_ID + "/" + BURGER_ID))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithoutAccess() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + KFC_ID + "/" + BURGER_ID))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MACDONALDS_ID + "/dishes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(burger, bigBurger));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MACDONALDS_ID + "/dishes/" + BURGER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(BURGER_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteWithoutAccess() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MACDONALDS_ID + "/dishes/" + BURGER_ID))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MACDONALDS_ID + "/dishes/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        DishTo newTo = convertToDishTo(getNew());
        Dish newDish = createNewFromTo(newTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("restaurantId", String.valueOf(MACDONALDS_ID))
                .content(JsonUtil.writeValue(newDish)));

        Dish created = MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        MATCHER.assertMatch(created, newDish);
        MATCHER.assertMatch(repository.getById(newId), newDish);
    }

//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    @WithUserDetails(value = ADMIN_MAIL)
//    void createDuplicate() throws Exception {
//        Dish duplicate = new Dish(null, burger.getName(), 200 );
//        perform(MockMvcRequestBuilders.post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("restaurantId", String.valueOf(MACDONALDS_ID))
//                .content(JsonUtil.writeValue(duplicate)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, 200);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithoutAccess() throws Exception {
        Dish newDish = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        DishTo updated = convertToDishTo(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL + MACDONALDS_ID + "/dishes/" + BURGER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MATCHER.assertMatch(repository.getById(BURGER_ID), getUpdated());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        DishTo invalid = convertToDishTo(new Dish(bigBurger));
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL + MACDONALDS_ID + "/dishes/" + BIG_BURGER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateWithoutAccess() throws Exception {
        DishTo invalid = convertToDishTo(new Dish(bigBurger));
        perform(MockMvcRequestBuilders.put(REST_URL + MACDONALDS_ID + "/dishes/" + BIG_BURGER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}