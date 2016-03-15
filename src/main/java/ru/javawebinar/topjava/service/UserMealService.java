package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {

    UserMeal save(UserMeal meal, int userId);

    void delete(int mealId, int userId) throws NotFoundException;

    UserMeal get(int mealId, int userId) throws NotFoundException;

    List<UserMeal> getAll(int userId);

    void update(UserMeal meal, int userId);

    List<UserMeal> getFiltered(LocalDate from, LocalDate to, int userId);
}
