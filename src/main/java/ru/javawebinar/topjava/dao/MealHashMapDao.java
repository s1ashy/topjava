package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealHashMapDao implements MealDao {
    private static final MealDao INSTANCE = new MealHashMapDao();
    private AtomicLong idCounter;
    private final Map<Long, Meal> mealMap;

    private MealHashMapDao() {
        mealMap = new ConcurrentHashMap<>();
        idCounter = new AtomicLong(0);
        reset();
    }

    @Override
    public Meal findById(long id) {
        return mealMap.get(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public void save(Meal meal) {
        long id = idCounter.incrementAndGet();
        meal.setId(id);
        mealMap.put(id, meal);
    }

    @Override
    public void delete(long id) {
        mealMap.remove(id);
    }

    @Override
    public synchronized void reset() {
        idCounter.set(0);
        mealMap.clear();

        // Mock-up data for tests
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

    }


    public static MealDao getInstance() {
        return INSTANCE;
    }
}
