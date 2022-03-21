package com.DigitalWindTeam.Justudy.controllers.v1;

import com.DigitalWindTeam.Justudy.models.User;
import com.DigitalWindTeam.Justudy.models.UserCourses;
import com.DigitalWindTeam.Justudy.sql.CourseDAO;
import com.DigitalWindTeam.Justudy.sql.UserDAO;
import com.DigitalWindTeam.Justudy.utils.TokenUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1")
public class UserController {

    private final UserDAO userDAO;
    private final CourseDAO courseDAO;

    @Autowired
    public UserController(UserDAO userDAO, CourseDAO courseDAO) {
        this.userDAO = userDAO;
        this.courseDAO = courseDAO;
    }

    @GetMapping("/profile")
    @JsonView(AuthController.class)
    public ResponseEntity getProfile(@RequestParam("token") String token) {
        try {
            Map<String, Object> tokenResult = TokenUtils.checkToken(token);
            if ((int) tokenResult.get("status") == 2) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            if ((int) tokenResult.get("status") == 1) {
                return new ResponseEntity(HttpStatus.BAD_GATEWAY); // check
            }
            JSONObject jsonObject = (JSONObject) tokenResult.get("data");
            int user_id = jsonObject.getAsNumber("id").intValue();
            return new ResponseEntity(userDAO.getUser(user_id), HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUserCourse")
    public ResponseEntity getUserCourse(@RequestParam("token") String token) {
        try {
            Map<String, Object> tokenResult = TokenUtils.checkToken(token);
            if ((int) tokenResult.get("status") == 2) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            if ((int) tokenResult.get("status") == 1) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            JSONObject jsonObject = (JSONObject) tokenResult.get("data");
            int user_id = jsonObject.getAsNumber("id").intValue();
            List<UserCourses> userCourses = userDAO.getUserCourse(user_id);
            List<Object> list = new ArrayList<>();
            for (UserCourses userCourses1 : userCourses) {
                list.add(courseDAO.getCourse(userCourses1.getCourse_id()));
            }
            Map<String, Object> resp = new HashMap<>();
            resp.put("courses", list);
            resp.put("user_id", user_id);

            return new ResponseEntity(resp, HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/joinCourse")
    public ResponseEntity joinCourse(@RequestParam("course_id") int course_id,
                                     @RequestParam("token") String token) {
        // TODO переделать под post запрос
        try {
            Map<String, Object> tokenResult = TokenUtils.checkToken(token);
            if ((int) tokenResult.get("status") == 2) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            if ((int) tokenResult.get("status") == 1) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            JSONObject jsonObject = (JSONObject) tokenResult.get("data");
            int user_id = jsonObject.getAsNumber("id").intValue();
            UserCourses userCourses = userDAO.joinCourse(user_id, course_id);
            if (userCourses == null) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(userCourses, HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/unjoinCourse")
    public ResponseEntity unjoinCourse(@RequestParam("course_id") int course_id,
                                       @RequestParam("token") String token) {
        try {
            Map<String, Object> tokenResult = TokenUtils.checkToken(token);
            if ((int) tokenResult.get("status") == 2) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            if ((int) tokenResult.get("status") == 1) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            JSONObject jsonObject = (JSONObject) tokenResult.get("data");
            int user_id = jsonObject.getAsNumber("id").intValue();
            UserCourses userCourses = userDAO.unjoinCourse(user_id, course_id);
            if (userCourses == null) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(userCourses, HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
