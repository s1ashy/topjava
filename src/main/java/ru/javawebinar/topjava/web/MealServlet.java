package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealHashMapDao;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceFactory;

import javax.servlet.http.HttpServlet;

abstract class MealServlet extends HttpServlet {
    protected MealService mealService = MealServiceFactory.get(MealHashMapDao.getInstance());
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

}
