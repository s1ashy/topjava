package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            System.out.println(adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN)));
            System.out.println(adminUserController.getAll());

            // HW2
            UserMealRestController userMealRestController = appCtx.getBean(UserMealRestController.class);
            System.out.println("--- CREATING ---");
            UserMeal breakfast = new UserMeal(LocalDateTime.now(), "Breakfast", 700);
            UserMeal lunch = new UserMeal(LocalDateTime.now(), "Lunch", 900);
            UserMeal dinner = new UserMeal(LocalDateTime.now(), "Dinner", 400);
            userMealRestController.create(breakfast);
            userMealRestController.create(lunch);
            userMealRestController.create(dinner);
            userMealRestController.getAll().forEach(System.out::println);

            System.out.println("--- UPDATING BREAKFAST ---");
            breakfast = new UserMeal(breakfast.getId(), breakfast.getDateTime(), "updated breakfast", 777);
            userMealRestController.update(breakfast);
            userMealRestController.getAll().forEach(System.out::println);

            System.out.println("--- READING LUNCH ---");
            System.out.println(userMealRestController.get(lunch.getId()));

            System.out.println("--- DELETING DINNER ---");
            userMealRestController.delete(dinner.getId());
            userMealRestController.getAll().forEach(System.out::println);
        }
    }
}
