package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.util.*;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final ResultSetExtractor<User> RESULT_SET_EXTRACTOR = rs -> {
        User user = null;
        while (rs.next()) {
            Role role = Role.valueOf(rs.getString("role"));
            if (user == null) {
                user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                        rs.getString("password"), rs.getInt("calories_per_day"),
                        rs.getBoolean("enabled"), EnumSet.of(role));
            } else {
                user.getRoles().add(role);
            }
        }
        return user;
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
        }
        namedParameterJdbcTemplate.update("DELETE FROM user_roles WHERE user_id=:id", map);
        user.getRoles().forEach(role -> jdbcTemplate.update("INSERT INTO user_roles(user_id, role) VALUES(?, ?)", user.getId(), role.name()));

        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        return jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id WHERE id=?", RESULT_SET_EXTRACTOR, id);
    }

    @Override
    public User getByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id WHERE email=?", RESULT_SET_EXTRACTOR, email);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id ORDER BY name, email", rs -> {
            Map<Integer, User> usersById = new HashMap<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                Role role = Role.valueOf(rs.getString("role"));
                User user = usersById.get(id);
                if (user == null) {
                    user = new User(id, rs.getString("name"), rs.getString("email"),
                            rs.getString("password"), rs.getInt("calories_per_day"),
                            rs.getBoolean("enabled"), EnumSet.of(role));
                    usersById.put(id, user);
                } else {
                    user.getRoles().add(role);
                }
            }

            List<User> users = new ArrayList<>(usersById.values());
            users.sort((user1, user2) -> {
                int cmpByName = user1.getName().compareTo(user2.getName());
                return cmpByName != 0 ? cmpByName : user1.getEmail().compareTo(user2.getEmail());
            });
            return users;
        });
    }
}
