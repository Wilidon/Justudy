package com.DigitalWindTeam.Justudy.controllers.v1;


import com.DigitalWindTeam.Justudy.models.User;
import com.DigitalWindTeam.Justudy.repository.UserRepository;
import com.DigitalWindTeam.Justudy.utils.TokenUtils;
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
@RequestMapping("/v1/auth/")
public class AuthController {

    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;

    @Autowired
    public AuthController(UserRepository userRepository,
                          TokenUtils tokenUtils) {
        this.userRepository = userRepository;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping("/register")
    @ApiOperation(value = "Регистрация пользователя")
    public ResponseEntity register(@RequestBody User user) {
        if (user.getEmail().length() > 32 ||
            user.getPassword().length() > 32 ||
            user.getPassword().length() < 3 ||
            user.getName().length() > 32 ||
            user.getPhone().length() > 15||
            user.getSurname().length() > 32) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean status = userRepository.existsByEmail(user.getEmail());
        if (status) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User new_user = userRepository.save(user);
        System.out.println(new_user.getId());
        String token = tokenUtils.getToken(Math.toIntExact(new_user.getId()));

        new_user.setToken(token);
        return new ResponseEntity<>(new_user, HttpStatus.OK);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Вход в аккаунт")
    public ResponseEntity login(@RequestBody User user) {
        // TODO Хэширование паролей
        String email = user.getEmail();
        String password = user.getPassword();
        User current_user = userRepository.findByEmail(email);
        if (current_user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (current_user.getPassword().equals(password)) {
            String token = tokenUtils.getToken(current_user.getId());
            current_user.setToken(token);
            return new ResponseEntity<>(current_user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }


    @PostMapping("/checkEmail")
    @ApiOperation(value = "Проверяет почту", response = Boolean.class)
    public ResponseEntity<Boolean> checkEmail(@RequestBody Map<String, String> message) {
        if (message.get("email").length() > 32) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userRepository.existsByEmail(message.get("email")), HttpStatus.OK);

    }

    @PostMapping("/checkToken")
    @ApiOperation(value = "Проверяет Token на валидность", response = Boolean.class)
    public ResponseEntity checkValid(@RequestBody Map<String, String> message) {
        if (message.get("token").length() > 32) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Map<String, Object> result = tokenUtils.checkToken(message.get("token"));
            return new ResponseEntity( (int) result.get("status") == 0, HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity(false, HttpStatus.OK);
        }
    }
}