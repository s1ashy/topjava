package ru.javawebinar.topjava.model;

/**
 * GKislin
 * 11.01.2015.
 */
public class MealWithExceed extends Meal {
    protected final boolean exceed;

    public MealWithExceed(Meal meal, boolean exceed) {
        super(meal.getDateTime(), meal.getDescription(), meal.getCalories());
        this.id = meal.getId();
        this.exceed = exceed;
    }

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}
