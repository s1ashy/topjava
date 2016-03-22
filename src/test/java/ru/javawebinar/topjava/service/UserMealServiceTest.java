package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    private DbPopulator dbPopulator;

    @Autowired
    private UserMealService service;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
        MealTestData.reset();
    }

    @Test
    public void get() throws Exception {
        UserMeal testMeal = testUserMeals.get(0);
        MATCHER.assertEquals(testMeal, service.get(testMeal.getId(), USER_ID));

        testMeal = testAdminMeals.get(0);
        MATCHER.assertEquals(testMeal, service.get(testMeal.getId(), UserTestData.ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void getOtherUserMeal() throws Exception {
        for (UserMeal testMeal : testUserMeals) {
            service.get(testMeal.getId(), ADMIN_ID);
        }
    }

    @Test
    public void delete() throws Exception {
        UserMeal removed = testUserMeals.remove(4);
        service.delete(removed.getId(), USER_ID);
        MATCHER.assertCollectionEquals(testUserMeals, service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteOtherUserMeal() throws Exception {
        service.delete(testUserMeals.get(0).getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        LocalDate start = LocalDate.parse("2016-03-19");
        LocalDate end = LocalDate.parse("2016-03-20");
        List<UserMeal> expected = testUserMeals
                .stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalDate(), start, end))
                .collect(Collectors.toList());

        MATCHER.assertCollectionEquals(expected, service.getBetweenDates(start, end, USER_ID));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        LocalDateTime start = LocalDateTime.parse("2016-03-19T08:00");
        LocalDateTime end = LocalDateTime.parse("2016-03-20T15:00");
        List<UserMeal> expected = testUserMeals
                .stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime(), start, end))
                .collect(Collectors.toList());

        MATCHER.assertCollectionEquals(expected, service.getBetweenDateTimes(start, end, USER_ID));
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(testUserMeals, service.getAll(USER_ID));
        MATCHER.assertCollectionEquals(testAdminMeals, service.getAll(ADMIN_ID));
    }

    @Test
    public void update() throws Exception {
        UserMeal testMeal = testUserMeals.get(0);
        testMeal.setCalories(999);
        MATCHER.assertEquals(testMeal, service.update(testMeal, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateOtherUserMeal() throws Exception {
        UserMeal testMeal = testUserMeals.get(0);
        testMeal.setCalories(999);
        service.update(testMeal, ADMIN_ID);
    }

    @Test
    public void save() throws Exception {
        UserMeal testMeal = new UserMeal(LocalDateTime.parse("2016-03-20T13:13:13"), "test meal", 1234);
        UserMeal created = service.save(testMeal, USER_ID);
        testMeal.setId(created.getId());
        testUserMeals.add(created);
        List<UserMeal> expected = testUserMeals
                .stream()
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
        MATCHER.assertCollectionEquals(expected, service.getAll(USER_ID));
    }
}