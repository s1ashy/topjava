package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteMealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(DeleteMealServlet.class);
    private static UserMealService mealService = UserMealServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mealService.deleteMeal(Long.parseLong(request.getParameter("id")));
        LOG.debug("deleted meal #" + request.getParameter("id"));
        response.sendRedirect("list");
    }
}
