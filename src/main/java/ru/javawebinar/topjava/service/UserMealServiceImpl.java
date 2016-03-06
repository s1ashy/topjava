package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMealServiceImpl implements UserMealService {
    private static long idCounter = 0;
    private Map<Long, UserMeal> mealMap;
    private static UserMealService instance;

    private UserMealServiceImpl() {
    }

    public static UserMealService getInstance() {
        if (instance == null) {
            instance = new UserMealServiceImpl();
        }
        return instance;
    }

    @Override
    public UserMeal findMealById(long id) {
        return mealMap.get(id);
    }

    @Override
    public List<UserMeal> findAllMeals() {
        if (mealMap == null) {
            mealMap = new HashMap<>();

            // Mock-up data for tests
            saveMeal(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            saveMeal(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            saveMeal(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            saveMeal(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            saveMeal(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            saveMeal(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        }

        return new ArrayList<>(mealMap.values());
    }

    @Override
    public void saveMeal(UserMeal meal) {
        if (mealMap.containsKey(meal.getId())) {
            throw new RuntimeException("Meal with ID " + meal.getId() + " already exists. Use updateMeal for updating.");
        } else if (meal.getId() == 0) {
            long id = ++idCounter;
            meal.setId(id);
            mealMap.put(id, meal);
        } else {
            mealMap.put(meal.getId(), meal);
        }
    }

    @Override
    public void updateMeal(UserMeal meal) {
        final String errorPrefix = "Cannot update meal with ID " + meal.getId() + ": ";

        if (mealMap.containsKey(meal.getId())) {
            mealMap.put(meal.getId(), meal);
        } else if (meal.getId() == 0) {
            throw new RuntimeException(errorPrefix + "Zero id is not allowed.");
        } else {
            throw new RuntimeException(errorPrefix + "Use saveMeal for adding.");
        }
    }

    @Override
    public void deleteMeal(long id) {
        mealMap.remove(id);
    }

    @Override
    public void reset() {
        mealMap = null;
        idCounter = 0;
    }
}
