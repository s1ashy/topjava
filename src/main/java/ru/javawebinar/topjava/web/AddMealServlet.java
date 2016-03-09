package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddMealServlet extends MealFormServlet {

    @Override
    public void init() throws ServletException {
        actionAttr = "new";
        LoggedAction = "added";
    }

    @Override
    protected Meal postActions(HttpServletRequest request, HttpServletResponse response) {
        Meal meal = new Meal(dateTime, description, calories);
        mealService.save(meal);
        return meal;
    }
}
