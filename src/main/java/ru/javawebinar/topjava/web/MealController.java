package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Controller
@RequestMapping("/meals")
public class MealController {

    private static final String context = "/meals";

    @Autowired
    private UserMealRestController mealController;

    @RequestMapping(method = RequestMethod.GET)
    public String mealList(Model model, HttpSession session) {
        if (session.getAttribute("filterEnabled") == null) {
            session.setAttribute("filterEnabled", false);
        }

        if ((boolean) session.getAttribute("filterEnabled")) {
            model.addAttribute("mealList", mealController.getBetween(
                    (LocalDate) session.getAttribute("startDate"),
                    (LocalTime) session.getAttribute("startTime"),
                    (LocalDate) session.getAttribute("endDate"),
                    (LocalTime) session.getAttribute("endTime")));
        } else {
            model.addAttribute("mealList", mealController.getAll());
        }
        return "mealList";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String setFilter(@RequestParam Map<String, String> params,
                            HttpSession session) {

        session.setAttribute("startDate", TimeUtil.parseLocalDate(params.get("startDate")));
        session.setAttribute("endDate", TimeUtil.parseLocalDate(params.get("endDate")));
        session.setAttribute("startTime", TimeUtil.parseLocalTime(params.get("startTime")));
        session.setAttribute("endTime", TimeUtil.parseLocalTime(params.get("endTime")));
        session.setAttribute("filterEnabled", true);
        return "redirect:" + context;
    }

    @RequestMapping(value = "/reset-filter", method = RequestMethod.GET)
    public String resetFilter(HttpSession session) {
        session.setAttribute("filterEnabled", false);
        return "redirect:" + context;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String setupForm(@PathVariable int id, Model model) {
        final UserMeal meal = mealController.get(id);
        model.addAttribute("action", context + "/update");
        model.addAttribute("meal", meal);
        return "mealEdit";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String setupForm(Model model) {
        final UserMeal meal = new UserMeal(LocalDateTime.now(), "", 1000);
        model.addAttribute("action", context + "/create");
        model.addAttribute("meal", meal);
        return "mealEdit";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id, HttpSession session) {
        mealController.delete(id);
        System.out.println("session id: " + session.getId());
        System.out.println("start date: " + session.getAttribute("startDate"));
        return "redirect:" + context;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam Map<String, String> params) {
        mealController.create(new UserMeal(
                LocalDateTime.parse(params.get("dateTime")),
                params.get("description"),
                Integer.valueOf(params.get("calories"))));
        return "redirect:" + context;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam Map<String, String> params) {
        mealController.update(new UserMeal(
                        LocalDateTime.parse(params.get("dateTime")),
                        params.get("description"),
                        Integer.valueOf(params.get("calories"))),
                Integer.valueOf(params.get("id")));
        return "redirect:" + context;
    }

}
