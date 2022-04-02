package com.DigitalWindTeam.Justudy.controllers.v1;


import com.DigitalWindTeam.Justudy.models.Course;
import com.DigitalWindTeam.Justudy.models.CourseModule;
import com.DigitalWindTeam.Justudy.models.CourseModuleHeader;
import com.DigitalWindTeam.Justudy.models.CourseModuleHeaderStep;
import com.DigitalWindTeam.Justudy.repository.CourseModuleHeaderRepository;
import com.DigitalWindTeam.Justudy.repository.CourseModuleHeaderStepRepository;
import com.DigitalWindTeam.Justudy.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1/course/")
public class CourceController {

    private final CourseRepository courseRepository;
    private final CourseModuleHeaderRepository courseModuleHeaderRepository;
    private final CourseModuleHeaderStepRepository courseModuleHeaderStepRepository;


    @Autowired
    public CourceController(CourseRepository courseRepository,
                            CourseModuleHeaderRepository courseModuleHeaderRepository,
                            CourseModuleHeaderStepRepository courseModuleHeaderStepRepository) {
        this.courseRepository = courseRepository;
        this.courseModuleHeaderRepository = courseModuleHeaderRepository;
        this.courseModuleHeaderStepRepository = courseModuleHeaderStepRepository;
    }


    @GetMapping("/getAllCourse")
    public ResponseEntity<List<Course>> getAllCourse(@RequestParam(value = "limit", defaultValue = "100") int limit ) {
        List<Course> course = courseRepository.findAll();
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/getCourse")
    public ResponseEntity getCourse(@RequestParam("id") long id) {
        Course course = courseRepository.findById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);

    }

    @GetMapping("/getCourseModuleHeaders")
    public ResponseEntity getCourseModuleHeaders(@RequestParam("id") long id) {
        Optional<CourseModuleHeader> courseModuleHeader =
                courseModuleHeaderRepository.findById(id);
        return new ResponseEntity(courseModuleHeader, HttpStatus.OK);
    }

    @GetMapping("/getCourseModuleHeaderSteps")
    public ResponseEntity getCourseModuleHeaderSteps(@RequestParam("id") long id) {
        Optional<CourseModuleHeaderStep> courseModuleHeaderStep =
                courseModuleHeaderStepRepository.findById(id);
        return new ResponseEntity(courseModuleHeaderStep, HttpStatus.OK);
    }

}
