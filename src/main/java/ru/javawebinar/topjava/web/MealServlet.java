package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private UserMealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            controller = appCtx.getBean(UserMealRestController.class);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String selectedUser = request.getParameter("selectedLoggedUser");
        if (selectedUser != null) {
            LOG.debug("switched to user {}", selectedUser);
            LoggedUser.setId(Integer.valueOf(selectedUser));
            response.sendRedirect("meals");
            return;
        }

        if (request.getParameter("filter") != null) {
            LOG.debug("clicked filter");

            LocalDate fromDate = request.getParameter("fromDate").isEmpty() ?
                    LocalDate.MIN : LocalDate.parse(request.getParameter("fromDate"));

            LocalDate toDate = request.getParameter("toDate").isEmpty() ?
                    LocalDate.MAX : LocalDate.parse(request.getParameter("toDate"));
            
            LocalTime fromTime = request.getParameter("fromTime").isEmpty() ?
                    LocalTime.MIN : LocalTime.parse(request.getParameter("fromTime"));
            
            LocalTime toTime = request.getParameter("toTime").isEmpty() ?
                    LocalTime.MAX : LocalTime.parse(request.getParameter("toTime"));

            LOG.debug("Date filter: {} - {}", fromDate, toDate);
            LOG.debug("Time filter: {} - {}", fromTime, toTime);

            request.setAttribute("showResetLink", true);
            request.setAttribute("mealList", controller.getFiltered(fromDate, fromTime, toDate, toTime));
            request.setAttribute("userList", UserUtil.USER_LIST);
            request.setAttribute("loggedUserId", LoggedUser.id());

            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
            return;
        }

        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (userMeal.isNew()) {
            userMeal = controller.create(userMeal);
            LOG.info("Create {}", userMeal);
        } else {
            controller.update(userMeal);
            LOG.info("Update {}", userMeal);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        request.setAttribute("userList", UserUtil.USER_LIST);
        request.setAttribute("loggedUserId", LoggedUser.id());
        if (action == null) {
            LOG.info("List all");
            request.setAttribute("mealList", controller.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            controller.delete(id);
            response.sendRedirect("meals");
        } else {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now(), "", 1000) :
                    controller.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
