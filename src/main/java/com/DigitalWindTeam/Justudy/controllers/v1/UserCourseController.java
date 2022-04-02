package com.DigitalWindTeam.Justudy.controllers.v1;


import com.DigitalWindTeam.Justudy.models.*;
import com.DigitalWindTeam.Justudy.repository.*;
import com.DigitalWindTeam.Justudy.utils.TokenUtils;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1/userCourse/")
public class UserCourseController {

    private final CourseRepository courseRepository;
    private final UserCoursesRepository userCoursesRepository;
    private final UserCourseProgressRepository userCourseProgressRepository;
    private final TokenUtils tokenUtils;

    @Autowired
    public UserCourseController(CourseRepository courseRepository,
                                UserCoursesRepository userCoursesRepository,
                                UserCourseProgressRepository userCourseProgressRepository,
                                TokenUtils tokenUtils) {
        this.courseRepository = courseRepository;
        this.userCoursesRepository = userCoursesRepository;
        this.userCourseProgressRepository = userCourseProgressRepository;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping("/is_joined")
    public ResponseEntity checkCourse(@RequestParam("courseId") long courseId,
                                      @RequestParam("token") String token) {
        try {
            Map<String, Object> tokenResult = tokenUtils.checkToken(token);
            if ((int) tokenResult.get("status") == 2) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            if ((int) tokenResult.get("status") == 1) {
                return new ResponseEntity(HttpStatus.BAD_GATEWAY); // check
            }
            JSONObject jsonObject = (JSONObject) tokenResult.get("data");
            long user_id = jsonObject.getAsNumber("id").longValue();

            UserCourse userCourse = userCoursesRepository.findByUserIdAndCourseId(user_id, courseId);
            if (userCourse == null) {
                return new ResponseEntity(false, HttpStatus.OK);
            }
            return new ResponseEntity(true, HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
