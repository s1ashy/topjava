package ru.javawebinar.topjava.service;

import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void testGetEagerly() throws Exception {
        User fetchedUser = service.getEagerly(USER_ID);
        Assert.assertTrue(Hibernate.isInitialized(fetchedUser.getMeals()));
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.USER_MEALS, fetchedUser.getMeals());

        UserTestData.TestUser tu = new UserTestData.TestUser(
                USER.getId(), USER.getName(), USER.getEmail(), USER.getPassword(),
                USER.getCaloriesPerDay(), USER.isEnabled(), USER.getRoles());
        MATCHER.assertEquals(tu, fetchedUser);
    }

    @Test(expected = NotFoundException.class)
    public void testGetEagerlyNotFound() throws Exception {
        service.getEagerly(99);
    }
}
