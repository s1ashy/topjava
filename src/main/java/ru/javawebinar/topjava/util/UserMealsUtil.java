package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        UserMealService userMealService = UserMealServiceImpl.getInstance();

        System.out.println("--- BEFORE ---");
        getMealsWithExceeded(userMealService.findAllMeals(), 2000).forEach(System.out::println);

        userMealService.deleteMeal(5);
        UserMeal updated = userMealService.findMealById(2);
        updated.setCalories(1234);
        userMealService.updateMeal(updated);

        updated.setId(333);
        userMealService.updateMeal(updated);

        System.out.println("--- AFTER ---");
        getMealsWithExceeded(userMealService.findAllMeals(), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = getCaloriesByDate(mealList);

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> createWithExceed(um, caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> getCaloriesByDate(List<UserMeal> mealList) {
        return mealList.stream()
                .collect(
                        Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                                Collectors.summingInt(UserMeal::getCalories))
                );
    }

    public static List<UserMealWithExceed> getMealsWithExceeded(List<UserMeal> mealList, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = getCaloriesByDate(mealList);

        return mealList.stream()
                .map(um -> createWithExceed(um, caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static UserMealWithExceed createWithExceed(UserMeal um, boolean exceeded) {
        return new UserMealWithExceed(um, exceeded);
    }
}