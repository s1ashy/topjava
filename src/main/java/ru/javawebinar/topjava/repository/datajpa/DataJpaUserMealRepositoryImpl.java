package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{
    private static final Sort SORT_DATETIME_DESC = new Sort(Sort.Direction.DESC, "dateTime");

    @Autowired
    private ProxyMealRepository proxy;

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserMeal save(UserMeal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);

        if (meal.isNew()) {
            return proxy.save(meal);
        } else {
            return proxy.update(meal.getId(), meal.getDateTime(), meal.getDescription(),
                    meal.getCalories(), userId) == 0 ? null : meal;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxy.delete(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return proxy.findOne(id, userId);
    }

    @Override
    public UserMeal getEagerly(int mealId) {
        return proxy.getEagerly(mealId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxy.findAll(userId, SORT_DATETIME_DESC);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxy.getBetween(startDate, endDate, userId, SORT_DATETIME_DESC);
    }
}
