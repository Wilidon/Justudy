package com.DigitalWindTeam.Justudy.controllers.v1;


import com.DigitalWindTeam.Justudy.models.Course;
import com.DigitalWindTeam.Justudy.models.User;
import com.DigitalWindTeam.Justudy.sql.CourseDAO;
import com.DigitalWindTeam.Justudy.sql.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1")
public class CourceController {

    private final CourseDAO courseDAO;

    @Autowired
    public CourceController(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @GetMapping("/getAllCourse")
    public List<Course> getAllCourse(@RequestParam(value = "limit", defaultValue = "100") int limit ) {
        return courseDAO.getAllCourse(limit);
    }

    @GetMapping("/getCourse")
    public ResponseEntity getCourse(@RequestParam("id") int id) {
         Course course = courseDAO.getCourse(id);
         if (course == null) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
         return new ResponseEntity<>(course, HttpStatus.OK);
    }

}
