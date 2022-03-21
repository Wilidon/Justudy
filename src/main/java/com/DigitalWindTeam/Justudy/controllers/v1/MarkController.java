package com.DigitalWindTeam.Justudy.controllers.v1;

import com.DigitalWindTeam.Justudy.sql.MarkDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1")
public class MarkController {

    private final MarkDAO markDAO;

    @Autowired
    public MarkController(MarkDAO markDAO) {
        this.markDAO = markDAO;
    }

    @GetMapping("/setMark")
    public ResponseEntity setMark(@RequestParam("course_id") int course_id) {


        return new ResponseEntity(HttpStatus.OK);
    }
}
