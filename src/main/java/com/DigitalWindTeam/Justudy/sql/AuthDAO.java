package com.DigitalWindTeam.Justudy.sql;

import com.DigitalWindTeam.Justudy.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class AuthDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean checkEmail(String email) {
        List<String> result = jdbcTemplate.query("SELECT email FROM users WHERE email = ?", new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1);
            }
        }, email);
        if (result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public User register(User user) {
        jdbcTemplate.update("INSERT INTO users (email, name, surname, phone, password, ip) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                user.getEmail(), user.getName(), user.getSurname(), user.getPhone(),
                user.getPassword(), user.getIp());
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?", new BeanPropertyRowMapper<>(User.class), user.getEmail());

    }

    public User login(String email, String password) {
//        return jdbcTemplate.query("",
//                new BeanPropertyRowMapper<>(User.class), email, password);
        String sql = "SELECT id, email, name, surname, phone FROM users WHERE email = ? AND password = ? LIMIT 1";
        User user = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class),
                email, password).stream().findFirst().orElse(null);
        System.out.println("db: " + user.getId());
        return user;
    }
}
