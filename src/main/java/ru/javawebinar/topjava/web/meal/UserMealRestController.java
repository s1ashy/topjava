package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    @Autowired
    private UserMealService service;

    public List<UserMealWithExceed> getAll() {
        return UserMealsUtil.getWithExceeded(service.getAll(LoggedUser.id()), LoggedUser.getCaloriesPerDay());
    }

    public List<UserMealWithExceed> getFiltered(LocalDateTime from, LocalDateTime to) {
        return getAll().stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime(), from, to))
                .collect(Collectors.toList());
    }

    public UserMeal create(UserMeal meal) {
        return service.save(meal, LoggedUser.id());
    }

    public void update(UserMeal meal) {
        service.update(meal, LoggedUser.id());
    }

    public void delete(int id) {
        service.delete(id, LoggedUser.id());
    }

    public UserMeal get(int id) {
        return service.get(id, LoggedUser.id());
    }
}
