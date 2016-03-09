package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

abstract class MealFormServlet extends MealServlet {
    protected String actionAttr, LoggedAction;

    protected LocalDateTime dateTime;
    protected String description;
    protected int calories;


    protected final ResourceBundle messages = ResourceBundle.getBundle("messages");
    protected final ResourceBundle appConfig = ResourceBundle.getBundle("application");
    protected String dtFormat = appConfig.getString("dateTimeFormat");
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dtFormat);


    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getActions(request, response);
        request.setAttribute("action", actionAttr);
        request.setAttribute("mustBeInteger", messages.getString("mustBeInteger"));
        request.getRequestDispatcher("mealForm.jsp").forward(request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
        description = request.getParameter("description");
        calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = postActions(request, response);

        LOG.debug(LoggedAction + ": " + meal);
        response.sendRedirect("list");
    }

    protected abstract Meal postActions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected void getActions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
