package ru.javawebinar.topjava.service;

import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserMealServiceTest extends UserMealServiceTest {

    @Test
    public void testGetWithUser() throws Exception {
        UserMeal meal = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        User mealUser = meal.getUser();
        Assert.assertTrue(Hibernate.isInitialized(meal.getUser()));

        UserTestData.TestUser tu = new UserTestData.TestUser(
                ADMIN.getId(), ADMIN.getName(), ADMIN.getEmail(), ADMIN.getPassword(),
                ADMIN.getCaloriesPerDay(), ADMIN.isEnabled(), ADMIN.getRoles());
        UserTestData.MATCHER.assertEquals(tu, mealUser);

        MATCHER.assertEquals(ADMIN_MEAL, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithUserNotFound() throws Exception {
        service.getWithUser(ADMIN_MEAL_ID, USER_ID);
    }

}
