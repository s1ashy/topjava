package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealListServlet extends MealServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealWithExceed> mealList = MealsUtil.getWithExceeded(mealService.findAll(), 2000);
        request.setAttribute("meals", mealList);
        request.getRequestDispatcher("/meals/mealList.jsp").forward(request, response);
    }
}
