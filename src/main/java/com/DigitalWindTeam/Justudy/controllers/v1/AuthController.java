package com.DigitalWindTeam.Justudy.controllers.v1;


import com.DigitalWindTeam.Justudy.models.User;
import com.DigitalWindTeam.Justudy.sql.AuthDAO;
import com.DigitalWindTeam.Justudy.utils.TokenUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.nimbusds.jose.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1")
public class AuthController {


    private final AuthDAO authDAO;

    @Autowired
    public AuthController(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    @PostMapping("/register")
    @JsonView(AuthController.class)
    @ApiOperation(value = "Регистрация пользователя", response = Boolean.class)
    public ResponseEntity<User> register(@RequestBody User user) {
        if (authDAO.checkEmail(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User new_user = authDAO.register(user);
        String token = TokenUtils.getToken(new_user.getId());

        new_user.setToken(token);
        return new ResponseEntity(new_user, HttpStatus.OK);

    }

    @PostMapping("/login")
    @JsonView(AuthController.class)
    @ApiOperation(value = "Вход в аккаунт", response = Boolean.class)
    public ResponseEntity login(@RequestBody User user) {
        // TODO Хэширование паролей
        user = authDAO.login(user.getEmail(), user.getPassword());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = TokenUtils.getToken(user.getId());
        user.setToken(token);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("/checkEmail")
    @ApiOperation(value = "Проверяет почту", response = Boolean.class)
    public Boolean checkEmail(@RequestBody Map<String, String> message) {
        return authDAO.checkEmail(message.get("email"));

    }

    @PostMapping("/checkToken")
    @ApiOperation(value = "Проверяет Token на валидность", response = Boolean.class)
    public Boolean checkValid(@RequestBody Map<String, String> message) {
        try {
            Map<String, Object> result = TokenUtils.checkToken(message.get("token"));
            return (int) result.get("status") == 0;
        } catch (ParseException | JOSEException e) {
            return false;
        }
    }
}