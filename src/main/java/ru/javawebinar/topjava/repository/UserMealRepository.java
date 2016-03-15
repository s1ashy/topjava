package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(UserMeal userMeal, int userId);

    boolean delete(int mealId, int userId);

    UserMeal get(int mealId, int userId);

    List<UserMeal> getAll(int userId);

    List<UserMeal> getFiltered(LocalDate from, LocalDate to, int userId);
}
