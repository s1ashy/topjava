package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface ProxyMealRepository extends JpaRepository<UserMeal, Integer> {

    @Query("SELECT m FROM UserMeal m WHERE m.user.id=?1")
    List<UserMeal> findAll(Integer userId, Sort sort);

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    UserMeal save(UserMeal meal);

    @Transactional
    @Modifying
    @Query("UPDATE UserMeal m SET m.dateTime=:dateTime, m.description=:description, " +
            "m.calories=:calories WHERE m.id=:mealId AND m.user.id=:userId")
    int update(@Param("mealId") int mealId,
               @Param("dateTime") LocalDateTime dateTime,
               @Param("description") String description,
               @Param("calories") int calories,
               @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal m WHERE m.id=?1 AND m.user.id=?2")
    int delete(Integer id, Integer userId);

    @Query("SELECT m FROM UserMeal m WHERE m.id=?1 AND m.user.id=?2")
    UserMeal findOne(Integer id, Integer userId);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT m FROM UserMeal m "+
            "WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate")
    List<UserMeal> getBetween(@Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate,
                              @Param("userId") int userId,
                              Sort sortDatetimeDesc);

    @Query("SELECT m FROM UserMeal m JOIN FETCH m.user WHERE m.id=?1")
    UserMeal getEagerly(int mealId);
}
