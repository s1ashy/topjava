package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteMealServlet extends MealServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mealService.delete(Long.parseLong(request.getParameter("id")));
        LOG.debug("deleted meal #" + request.getParameter("id"));
        response.sendRedirect("list");
    }
}
