package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class UserMealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserMealRestController.REST_URL + "/";

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEED.contentListMatcher(UserMealsUtil.getWithExceeded(
                        USER_MEALS, UserTestData.USER.getCaloriesPerDay())));
    }

    @Test
    public void testGetWithAck() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_MEAL_ID).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteWithAck() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());

        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2),
                mealService.getAll(USER_ID));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + ADMIN_MEAL_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateWithAck() throws Exception {
        UserMeal updated = getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        mockMvc.perform(put(REST_URL + ADMIN_MEAL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdated())))
                .andExpect(status().isNotFound());

        MATCHER.assertEquals(MEAL1, mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testCreateWithLocation() throws Exception {
        ResultActions result = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getCreated())))
                .andDo(print())
                .andExpect(status().isCreated());

        UserMeal returned = MATCHER.fromJsonAction(result);
        UserMeal expected = new UserMeal(returned.getId(), getCreated().getDateTime(),
                getCreated().getDescription(), getCreated().getCalories());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1),
                mealService.getAll(USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        LocalDateTime start = LocalDateTime.of(2015, Month.MAY, 31, 12, 0, 0);
        LocalDateTime end = LocalDateTime.of(2015, Month.MAY, 31, 23, 0, 0);

        List<UserMealWithExceed> expected = UserMealsUtil.getFilteredWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4),
                start.toLocalTime(), end.toLocalTime(), USER.getCaloriesPerDay());

        mockMvc.perform(get(REST_URL + "between?"
                + "from=" + start.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                + "&to=" + end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MATCHER_EXCEED.contentListMatcher(expected));

    }

}