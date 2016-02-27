package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> mealsWithExceed = new ArrayList<>();

        // Count calories consumed per day
        Map<LocalDate, Integer> calCounters = new HashMap<>();
        mealList.forEach(meal -> {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            int counter = calCounters.containsKey(mealDate) ? calCounters.get(mealDate) : 0;
            counter += meal.getCalories();
            calCounters.put(mealDate, counter);
        });

        mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(meal -> {
                    boolean hasExceeded = calCounters.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;

                    // OK, next line could instead have:
                    // new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), isExceed)
                    // ...but it's much more convenient with a dedicated constructor, isn't it?
                    mealsWithExceed.add(new UserMealWithExceed(meal, hasExceeded));
                });

        // Test
        System.out.println(calCounters);
        mealsWithExceed.forEach(System.out::println);
        // endTest
        return mealsWithExceed;
    }
}
