package com.DigitalWindTeam.Justudy.repository;

import com.DigitalWindTeam.Justudy.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findById(long id);

    @Transactional
    @Modifying
    @Query("UPDATE Course c SET c.participants = c.participants + 1 where c.id = ?1")
    int updateParticipants(long id);

}
