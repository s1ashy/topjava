package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.dao.MealHashMapDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealsUtil {
    public static void main(String[] args) {
        MealService mealService = MealServiceFactory.get(MealHashMapDao.getInstance());

        System.out.println("--- BEFORE ---");
        getWithExceeded(mealService.findAll(), 2000).forEach(System.out::println);

        mealService.delete(5);
        Meal updated = mealService.findById(2);
        updated.setCalories(1234);
        mealService.update(updated);

        updated.setId(333);
        mealService.update(updated);

        System.out.println("--- AFTER ---");
        getWithExceeded(mealService.findAll(), 2000).forEach(System.out::println);
    }

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = getCaloriesByDate(mealList);

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> createWithExceed(um, caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> getCaloriesByDate(List<Meal> mealList) {
        return mealList.stream()
                .collect(
                        Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                                Collectors.summingInt(Meal::getCalories))
                );
    }

    public static List<MealWithExceed> getWithExceeded(List<Meal> mealList, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = getCaloriesByDate(mealList);

        return mealList.stream()
                .map(um -> createWithExceed(um, caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static MealWithExceed createWithExceed(Meal um, boolean exceeded) {
        return new MealWithExceed(um, exceeded);
    }
}