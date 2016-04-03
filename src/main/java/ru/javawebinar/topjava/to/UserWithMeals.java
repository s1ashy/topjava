package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class UserWithMeals extends User {

    protected List<UserMeal> meals;

    public UserWithMeals(User user, List<UserMeal> meals) {
        super(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCaloriesPerDay(),
                user.isEnabled(),
                user.getRoles());

        this.meals = meals;
    }

    public List<UserMeal> getMeals() {
        return meals;
    }

    @Override
    public String toString() {
        return "UserWithMeals (" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                ", meals=" + meals +
                ')';
    }
}
