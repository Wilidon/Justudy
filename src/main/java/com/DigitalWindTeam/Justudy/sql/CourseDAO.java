package com.DigitalWindTeam.Justudy.sql;

import com.DigitalWindTeam.Justudy.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Course> getAllCourse(int limit) {
        return jdbcTemplate.query("SELECT * FROM courses LIMIT ?", new BeanPropertyRowMapper<>(Course.class), limit);
    }

    public Course getCourse(int id) {
        return jdbcTemplate.query("SELECT * FROM courses WHERE id = ? LIMIT 1",
                new BeanPropertyRowMapper<>(Course.class),
                id).stream().findAny().orElse(null);
    }

//    public User getUser(int id) {
//        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
//    }
}