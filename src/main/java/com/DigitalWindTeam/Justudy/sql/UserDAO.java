package com.DigitalWindTeam.Justudy.sql;

import com.DigitalWindTeam.Justudy.models.User;
import com.DigitalWindTeam.Justudy.models.UserCourses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT * FROM users LIMIT 3", new BeanPropertyRowMapper<>(User.class));
    }

    public User getUser(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ? LIMIT 1",
                new BeanPropertyRowMapper<>(User.class),
                id);
    }

    public User findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users WHERE email = ? LIMIT 1",
                new BeanPropertyRowMapper<>(User.class),
                email).stream().findAny().orElse(null);
    }

    public List<UserCourses> getUserCourse(int user_id) {
        return jdbcTemplate.query("SELECT * FROM user_courses WHERE user_id = ?",
                new BeanPropertyRowMapper<>(UserCourses.class),
                user_id);
    }

    public UserCourses joinCourse(int user_id, int course_id) {
        UserCourses userCourses = jdbcTemplate.query("SELECT * FROM user_courses WHERE user_id = ? AND course_id = ?",
                new BeanPropertyRowMapper<>(UserCourses.class), user_id, course_id).stream().findFirst().orElse(null);
        if (userCourses == null) {
            final String sql = "INSERT INTO user_courses (user_id, course_id) " +
                    "VALUES (?, ?)";
            jdbcTemplate.update(sql, user_id, course_id);
            return jdbcTemplate.query("SELECT * FROM user_courses WHERE user_id = ? AND course_id = ?",
                    new BeanPropertyRowMapper<>(UserCourses.class), user_id, course_id).stream().findFirst().orElse(null);
        }
        else {
            return null;
        }

    }

    public UserCourses unjoinCourse(int user_id, int course_id) {
        UserCourses userCourses = jdbcTemplate.query("SELECT * FROM user_courses WHERE user_id = ? AND course_id = ?",
                new BeanPropertyRowMapper<>(UserCourses.class), user_id, course_id).stream().findFirst().orElse(null);
        if (userCourses != null) {
            final String sql = "INSERT INTO user_courses (user_id, course_id) " +
                    "VALUES (?, ?)";
            jdbcTemplate.update(sql, user_id, course_id);
            return jdbcTemplate.query("SELECT * FROM user_courses WHERE user_id = ? AND course_id = ?",
                    new BeanPropertyRowMapper<>(UserCourses.class), user_id, course_id).stream().findFirst().orElse(null);
        }
        else {
            return null;
        }
    }


//    public User getUser(int id) {
//        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
//    }
}
