package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.MealDao;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MealServiceFactory {
    private static final ConcurrentMap<MealDao, MealService> instances = new ConcurrentHashMap<>();

    /**
     * Get MealService instance by MealDao.
     * @param mealDao <a href="ru.javawebinar.topjava.dao.MealDao">MealDao</a> that
     *                returned service will use
     * @return <a href="ru.javawebinar.topjava.service.MealService">MealService</a> instance
     * that uses specified DAO
     */
    public static MealService get(MealDao mealDao) {
        if (!instances.containsKey(mealDao)) {
            instances.put(mealDao, new AbstractMealService() {
            @Override
            protected MealDao initMealDao() {
                return mealDao;
            }});
        }

        return instances.get(mealDao);
    }
}
