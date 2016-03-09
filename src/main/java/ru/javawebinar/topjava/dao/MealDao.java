package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal findById(long id);

    List<Meal> findAll();

    void save(Meal meal);

    void delete(long id);

    void reset();    // reset method used for testing

}
