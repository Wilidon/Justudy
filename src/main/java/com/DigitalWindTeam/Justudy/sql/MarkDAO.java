package com.DigitalWindTeam.Justudy.sql;

import com.DigitalWindTeam.Justudy.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarkDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MarkDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT * FROM users LIMIT 3", new BeanPropertyRowMapper<>(User.class));
    }
}
