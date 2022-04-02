package com.DigitalWindTeam.Justudy.repository;

import com.DigitalWindTeam.Justudy.models.Course;
import com.DigitalWindTeam.Justudy.models.CourseModuleHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface CourseModuleHeaderRepository extends JpaRepository<CourseModuleHeader, Long> {

}
