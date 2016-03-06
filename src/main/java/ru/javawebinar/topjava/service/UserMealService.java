package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

public interface UserMealService {
    UserMeal findMealById(long id);

    List<UserMeal> findAllMeals();

    void saveMeal(UserMeal meal);

    void updateMeal(UserMeal meal);

    void deleteMeal(long id);

    void reset();
}
