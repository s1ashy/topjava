package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/profile/meals")
public class UserMealAjaxController extends AbstractUserMealController {
    private static final Logger LOG = LoggerFactory.getLogger(UserMealAjaxController.class);

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getBetween(@RequestParam(required = false) LocalDate startDate,
                                               @RequestParam(required = false) LocalTime startTime,
                                               @RequestParam(required = false) LocalDate endDate,
                                               @RequestParam(required = false) LocalTime endTime,
                                               HttpSession session) {
        LOG.debug("getBetween called");
        session.setAttribute("startDate", startDate);
        session.setAttribute("startTime", startTime);
        session.setAttribute("endDate", endDate);
        session.setAttribute("endTime", endTime);
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @RequestMapping(params = "keepFilter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getLastFilter(@RequestParam boolean keepFilter,
                                                  HttpSession session) {
        LOG.debug("getLastFilter called (" + keepFilter + ")");
        if (keepFilter) {
            return super.getBetween(
                    (LocalDate) session.getAttribute("startDate"),
                    (LocalTime) session.getAttribute("startTime"),
                    (LocalDate) session.getAttribute("endDate"),
                    (LocalTime) session.getAttribute("endTime"));
        }
        else {
            return super.getAll();
        }
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createOrUpdate(@RequestParam("id") int id,
                               @RequestParam("date") LocalDateTime dateTime,
                               @RequestParam("description") String description,
                               @RequestParam("calories") int calories) {
        UserMeal meal = new UserMeal(id, dateTime, description, calories);
        if (id == 0) {
            super.create(meal);
        } else {
            super.update(meal, id);
        }
    }
}
