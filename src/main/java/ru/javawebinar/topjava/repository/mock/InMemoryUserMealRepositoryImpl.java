package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.UserUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(
                meal -> save(meal, ThreadLocalRandom.current().nextInt(1, UserUtil.USER_LIST.size() + 1)));
        UserMealsUtil.MEAL_LIST.stream().forEach(System.out::println);
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        } else if (!isUserOwner(userMeal.getId(), userId)) {
            return null;
        }
        userMeal.setOwnerId(userId);
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int mealId, int userId) {
        if (isUserOwner(mealId, userId)) {
            repository.remove(mealId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserMeal get(int mealId, int userId) {
        if (isUserOwner(mealId, userId)) {
            return repository.get(mealId);
        } else {
            return null;
        }
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getOwnerId() == userId)
                .sorted((m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserMeal> getFiltered(LocalDate from, LocalDate to, int userId) {
        return getAll(userId)
                .stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalDate(), from, to))
                .collect(Collectors.toList());
    }

    private boolean isUserOwner(int mealId, int userId) {
        return repository.containsKey(mealId) && userId == repository.get(mealId).getOwnerId();
    }
}
