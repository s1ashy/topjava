package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditMealServlet extends MealFormServlet {

    @Override
    public void init() throws ServletException {
        actionAttr = "edit";
        LoggedAction = "updated";
    }

    @Override
    protected void getActions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final long id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("meal", mealService.findById(id));
    }

    @Override
    protected Meal postActions(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("id"));
        Meal meal = mealService.findById(id);

        meal.setDateTime(dateTime);
        meal.setDescription(description);
        meal.setCalories(calories);

        mealService.update(meal);
        return meal;
    }
}
