package ru.javawebinar.topjava.repository.jpa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JpaUserMealRepositoryImplTest {

    @Autowired
    private UserMealRepository repository;

    @Test
    public void save() throws Exception {
        UserMeal created = MealTestData.getCreated();
        repository.save(created, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), repository.getAll(USER_ID));
    }

    @Test
    public void update() throws Exception {
        UserMeal updated = MealTestData.getUpdated();
        updated.setUser(USER);
        repository.save(updated, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, updated), repository.getAll(USER_ID));
    }

    @Test
    public void updateNotFound() throws Exception {
        UserMeal updated = MealTestData.getUpdated();
        Assert.assertNull(repository.save(updated, ADMIN_ID));
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), repository.getAll(USER_ID));
    }

    @Test
    public void delete() throws Exception {
        Assert.assertTrue(repository.delete(MEAL1_ID, USER_ID));
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), repository.getAll(USER_ID));
    }

    @Test
    public void deleteNotFound() throws Exception {
        Assert.assertFalse(repository.delete(MEAL1_ID, ADMIN_ID));
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), repository.getAll(USER_ID));
    }

    @Test
    public void get() throws Exception {
        UserMeal actual = repository.get(MEAL1_ID, USER_ID);
        MATCHER.assertEquals(MEAL1, actual);
    }

    @Test
    public void getNotFound() throws Exception {
        UserMeal actual = repository.get(MEAL1_ID, ADMIN_ID);
        Assert.assertNull(actual);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(USER_MEALS, repository.getAll(USER_ID));
    }

    @Test
    public void getBetween() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2),
                repository.getBetween(LocalDateTime.of(2015, Month.MAY, 30, 12, 0), LocalDateTime.of(2015, Month.MAY, 30, 23, 59), USER_ID));
    }
}