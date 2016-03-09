package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

abstract class AbstractMealService implements MealService {
    private MealDao mealDao = initMealDao();

    protected abstract MealDao initMealDao();

    @Override
    public Meal findById(long id) {
        return mealDao.findById(id);
    }

    @Override
    public List<Meal> findAll() {
        return mealDao.findAll();
    }

    @Override
    public void save(Meal meal) {
        mealDao.save(meal);
    }

    @Override
    public void update(Meal meal) {
        Meal entity = mealDao.findById(meal.getId());
        if (entity != null) {
            entity.setDescription(meal.getDescription());
            entity.setCalories(meal.getCalories());
            entity.setDateTime(meal.getDateTime());
        }
    }

    @Override
    public void delete(long id) {
        mealDao.delete(id);
    }

    @Override
    public void reset() {
        mealDao.reset();
    }
}
