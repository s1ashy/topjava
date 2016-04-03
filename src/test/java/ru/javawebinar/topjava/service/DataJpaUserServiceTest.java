package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.to.UserWithMeals;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void testGetWithMeals() throws Exception {
        UserWithMeals userWithMeals = service.getWithMeals(USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.USER_MEALS, userWithMeals.getMeals());

        UserTestData.TestUser tu = new UserTestData.TestUser(
                USER.getId(), USER.getName(), USER.getEmail(), USER.getPassword(),
                USER.getCaloriesPerDay(), USER.isEnabled(), USER.getRoles());
        MATCHER.assertEquals(tu, userWithMeals);
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithMealsNotFound() throws Exception {
        service.getWithMeals(99);
    }
}
