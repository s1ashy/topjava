package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    Meal findById(long id);

    List<Meal> findAll();

    void save(Meal meal);

    void update(Meal meal);

    void delete(long id);

    void reset();
}
