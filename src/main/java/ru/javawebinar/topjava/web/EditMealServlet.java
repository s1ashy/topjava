package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class EditMealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(EditMealServlet.class);
    private static UserMealService mealService = UserMealServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to edit");

        final long id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("meal", mealService.findMealById(id));
        request.setAttribute("action", "edit");
        request.getRequestDispatcher("mealForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        long id = Long.parseLong(request.getParameter("id"));
        UserMeal meal = mealService.findMealById(id);

        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        mealService.updateMeal(meal);
        LOG.debug("updated: " + meal);
        response.sendRedirect("list");
    }
}
