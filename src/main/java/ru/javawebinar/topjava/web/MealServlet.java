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
import java.time.LocalDateTime;
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
            StringBuilder urlParams = new StringBuilder();
            String fromTime = request.getParameter("fromDateTime");
            if (!fromTime.isEmpty()) {
                urlParams.append("&fromDateTime=").append(fromTime);}

            String toTime = request.getParameter("toDateTime");
            if (!toTime.isEmpty()) {
                urlParams.append("&toDateTime=").append(toTime);
            }
            response.sendRedirect("meals?action=filter" + urlParams);
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
        } else if (action.equals("filter")) {
            LOG.info("Filter");

            LocalDateTime fromDateTime = LocalDateTime.MIN;
            try {
                fromDateTime = LocalDateTime.parse(request.getParameter("fromDateTime"));
            } catch (Exception e) {
                LOG.debug(request.getParameter("fromDateTime"));
                LOG.debug(e.getMessage());
            }
            request.setAttribute("fromDateTime", fromDateTime);
            request.setAttribute("showResetLink", true);
            LocalDateTime toDateTime = LocalDateTime.MAX;
            try {
                toDateTime = LocalDateTime.parse(request.getParameter("toDateTime"));
            } catch (Exception e) {
                LOG.debug(request.getParameter("toDateTime"));
                LOG.debug(e.getMessage());
            }
            request.setAttribute("toDateTime", toDateTime);

            request.setAttribute("mealList", controller.getFiltered(fromDateTime, toDateTime));
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
