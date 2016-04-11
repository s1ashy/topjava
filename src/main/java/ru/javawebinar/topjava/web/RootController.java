package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    @Autowired
    private UserService service;

    @Autowired
    private UserMealService mealService;

    @Autowired
    private UserMealRestController mealController;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userList", service.getAll());
        return "userList";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        LoggedUser.setId(userId);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList(@RequestParam(value = "action", required = false) String action,
                           @RequestParam(value = "id", required = false) Integer id,
                           Model model) {
        if (action == null) {
            model.addAttribute("mealList", mealController.getAll());
            return "mealList";
        } else if ("delete".equals(action)) {
            mealController.delete(id);
            return "redirect:meals";
        } else {
            final UserMeal meal = "create".equals(action) ?
                    new UserMeal(LocalDateTime.now(), "", 1000) :   // create
                    mealController.get(id);           // update
            model.addAttribute("meal", meal);
            return "mealEdit";
        }
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String editMeal(@RequestParam Map<String, String > params,
                           Model model) {
        String action = params.get("action");
        if (action == null) {
            final UserMeal userMeal = new UserMeal(
                    LocalDateTime.parse(params.get("dateTime")),
                    params.get("description"),
                    Integer.valueOf(params.get("calories")));

            if (params.get("id").isEmpty()) {
                mealController.create(userMeal);
            } else {
                mealController.update(userMeal, Integer.valueOf(params.get("id")));
            }
            return "redirect:meals";
        } else if ("filter".equals(action)) {
            LocalDate startDate = TimeUtil.parseLocalDate(params.get("startDate"));
            LocalDate endDate = TimeUtil.parseLocalDate(params.get("endDate"));
            LocalTime startTime = TimeUtil.parseLocalTime(params.get("startTime"));
            LocalTime endTime = TimeUtil.parseLocalTime(params.get("endTime"));
            model.addAttribute("mealList", mealController.getBetween(startDate, startTime, endDate, endTime));
            return "mealList";
        } else {
            return "redirect:mealList";
        }
    }

}
