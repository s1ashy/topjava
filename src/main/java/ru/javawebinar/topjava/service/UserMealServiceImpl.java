package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal save(UserMeal meal, int userId) {
        if (meal.getOwnerId() == null) {
            meal.setOwnerId(userId);
        }
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int mealId, int userId) throws NotFoundException {
        ExceptionUtil.check(repository.delete(mealId, userId), mealId);
    }

    @Override
    public UserMeal get(int mealId, int userId) throws NotFoundException {
        return ExceptionUtil.check(repository.get(mealId, userId), mealId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public void update(UserMeal meal, int userId) {
        if (meal.getOwnerId() == null) {
            meal.setOwnerId(userId);
        }
        repository.save(meal, userId);
    }
}
