package ru.javawebinar.topjava.repository.datajpa;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
@Profile("datajpa")
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{
    private static final Logger LOG = LoggerFactory.getLogger(DataJpaUserMealRepositoryImpl.class);
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
            return get(meal.getId(), userId) == null ? null : proxy.save(meal);
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
        UserMeal meal = proxy.findOne(mealId);
        if (meal == null) {
            return null;
        }
        Hibernate.initialize(meal.getUser());
        return meal;
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
