package com.DigitalWindTeam.Justudy.controllers.v1;

import com.DigitalWindTeam.Justudy.models.*;
import com.DigitalWindTeam.Justudy.repository.CourseRepository;
import com.DigitalWindTeam.Justudy.repository.UserCourseProgressRepository;
import com.DigitalWindTeam.Justudy.repository.UserCoursesRepository;
import com.DigitalWindTeam.Justudy.repository.UserRepository;
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
@RequestMapping("/v1/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserCoursesRepository userCoursesRepository;
    private final CourseRepository courseRepository;
    private final UserCourseProgressRepository userCourseProgressRepository;
    private final TokenUtils tokenUtils;

    @Autowired
    public UserController(UserRepository userRepository,
                          UserCoursesRepository userCoursesRepository,
                          CourseRepository courseRepository,
                          UserCourseProgressRepository userCourseProgressRepository,
                          TokenUtils tokenUtils) {
        this.userRepository = userRepository;
        this.userCoursesRepository = userCoursesRepository;
        this.courseRepository = courseRepository;
        this.userCourseProgressRepository = userCourseProgressRepository;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping("/profile")
    public ResponseEntity getProfile(@RequestParam("token") String token) {
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

            Map<String, Object> resp = new HashMap<>();
            Optional<User> user = userRepository.findById(user_id);
            resp.put("profile", user);

            List<UserCourse> userCourse = userCoursesRepository.findByUserId(user_id);
            List<Course> list = new ArrayList<>();
            for (UserCourse userCourse1 : userCourse) {
                list.add(courseRepository.findById(userCourse1.getCourseId()));
            }
            resp.put("course", list);
            return new ResponseEntity(resp, HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUserCourse")
    public ResponseEntity getUserCourse(@RequestParam("course_id") long course_id,
                                        @RequestParam("token") String token) {
        try {
            Map<String, Object> tokenResult = tokenUtils.checkToken(token);
            if ((int) tokenResult.get("status") == 2) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            if ((int) tokenResult.get("status") == 1) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            JSONObject jsonObject = (JSONObject) tokenResult.get("data");
            long user_id = jsonObject.getAsNumber("id").intValue();
            Map<String, Object> resp = new HashMap<>();

            UserCourse userCourse = userCoursesRepository.findByUserIdAndCourseId(user_id, course_id);


            return new ResponseEntity(userCourse, HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/joinCourse")
    public ResponseEntity joinCourse(@RequestParam("course_id") long course_id,
                                     @RequestParam("token") String token) {
        // TODO переделать под post запрос
        try {
            Map<String, Object> tokenResult = tokenUtils.checkToken(token);
            if ((int) tokenResult.get("status") == 2) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if ((int) tokenResult.get("status") == 1) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            JSONObject jsonObject = (JSONObject) tokenResult.get("data");
            long user_id = jsonObject.getAsNumber("id").intValue();
            UserCourse user = userCoursesRepository.findByUserIdAndCourseId(user_id, course_id);
            if (user != null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            // +1 участник курса
            courseRepository.updateParticipants(course_id);

            UserCourse userCourses = new UserCourse();
            userCourses.setUserId(user_id);
            userCourses.setCourseId(course_id);
            userCourses.setStatus("started");
            user = userCoursesRepository.save(userCourses);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/profile")
    public ResponseEntity updateProfile(@RequestBody User user) {
        try {
            Map<String, Object> tokenResult = tokenUtils.checkToken(user.getToken());
            if ((int) tokenResult.get("status") == 2) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if ((int) tokenResult.get("status") == 1) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            JSONObject jsonObject = (JSONObject) tokenResult.get("data");
            long user_id = jsonObject.getAsNumber("id").intValue();
            if (user.getEmail().length() > 32 ||
                    user.getName().length() > 32 ||
                    user.getPhone().length() > 15 ||
                    user.getSurname().length() > 32) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (!(user.getEmail() == null)) {
                userRepository.udpateEmail(user.getEmail(), user_id);
            }
            if (!(user.getPhone() == null)) {
                userRepository.udpatePhone(user.getPhone(), user_id);
            }
            if (!(user.getSurname() == null)) {
                userRepository.udpateSurname(user.getSurname(), user_id);
            }
            if (!(user.getName() == null)) {
                userRepository.udpateName(user.getName(), user_id);
            }
            if (!(user.getPassword() == null)) {
                userRepository.udpatePassword(user.getPassword(), user_id);
            }
            return new ResponseEntity(HttpStatus.OK);
        } catch (ParseException | JOSEException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // @GetMapping("/getCourse")


//    @GetMapping("/unjoinCourse")
//    public ResponseEntity unjoinCourse(@RequestParam("course_id") int course_id,
//                                       @RequestParam("token") String token) {
//        try {
//            Map<String, Object> tokenResult = tokenUtils.checkToken(token);
//            if ((int) tokenResult.get("status") == 2) {
//                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//            }
//            if ((int) tokenResult.get("status") == 1) {
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
//            }
//            JSONObject jsonObject = (JSONObject) tokenResult.get("data");
//            long user_id = jsonObject.getAsNumber("id").intValue();
//            UserCourses user = userCoursesRepository.findByUserIdAndCourseId(user_id, course_id);
//            if (user != null) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//            UserCourses userCourses = new UserCourses();
//            userCourses.setUserId(user_id);
//            userCourses.setCourseId(course_id);
//            userCourses.setStatus("started");
//            user = userCoursesRepository.save(userCourses);
//            return new ResponseEntity<>(user, HttpStatus.OK);
//            return new ResponseEntity(userCourses, HttpStatus.OK);
//        } catch (ParseException | JOSEException e) {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//}


}
