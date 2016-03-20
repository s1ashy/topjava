package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    private static int idCounter;
    public static List<UserMeal> testUserMeals;
    public static List<UserMeal> testAdminMeals;

    public static void reset() {
        idCounter = BaseEntity.START_SEQ + 1;

        testUserMeals = Arrays.asList(
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-19T09:00:00"), "User breakfast #1", 500),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-19T12:00:00"), "User lunch #1", 1000),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-19T19:00:00"), "User dinner #1", 500),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-20T09:00:00"), "User breakfast #2", 700),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-20T12:00:00"), "User lunch #2", 1000),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-20T19:30:00"), "User dinner #2", 500),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-21T09:00:00"), "User breakfast #3", 700),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-21T12:00:00"), "User lunch #3", 1000),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-21T19:30:00"), "User dinner #3", 500))
                .stream()
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());

        testAdminMeals = Arrays.asList(
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-19T09:00:00"), "Admin breakfast #1", 555),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-19T12:00:00"), "Admin lunch #1", 900),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-19T18:30:00"), "Admin dinner #1", 450),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-20T09:00:00"), "Admin breakfast #2", 800),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-20T12:00:00"), "Admin lunch #2", 950),
                new UserMeal(++idCounter, LocalDateTime.parse("2016-03-20T19:00:00"), "Admin dinner #2", 600))
                .stream()
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }
}
