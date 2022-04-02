package com.DigitalWindTeam.Justudy.repository;

import com.DigitalWindTeam.Justudy.models.Course;
import com.DigitalWindTeam.Justudy.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCoursesRepository extends JpaRepository<UserCourse, Long> {
    List<UserCourse> findByUserId(Long userId);
    UserCourse findByUserIdAndCourseId(Long userId, Long courseId);

}