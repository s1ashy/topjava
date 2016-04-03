package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public interface UserRepository {
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();

    default List<UserMeal> getMealsByUserId(int userId) {
        throw new IllegalStateException("Method is not implemented");
    }
}
