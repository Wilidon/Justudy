package com.DigitalWindTeam.Justudy.repository;

import com.DigitalWindTeam.Justudy.models.UserCourse;
import com.DigitalWindTeam.Justudy.models.UserCourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserCourseProgressRepository extends JpaRepository<UserCourseProgress, Long> {
//    @Transactional
//    @Modifying
//    @Query("UPDATE UserCourseProgress u SET u.status = 'completed' where u.userId = ?1 and")
//    int updateParticipants(long id);

    UserCourseProgress findByCourseIdAndStepId(long courseId, long stepId);

}
