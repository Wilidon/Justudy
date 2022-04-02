package com.DigitalWindTeam.Justudy.repository;

import com.DigitalWindTeam.Justudy.models.Course;
import com.DigitalWindTeam.Justudy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    //User findById(long id);
    User findByEmail(String email);
    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.email = ?1 where u.id = ?2")
    int udpateEmail(String email, long id);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = ?1 where u.id = ?2")
    int udpatePassword(String password, long id);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.name = ?1 where u.id = ?2")
    int udpateName(String name, long id);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.surname = ?1 where u.id = ?2")
    int udpateSurname(String surname, long id);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.phone = ?1 where u.id = ?2")
    int udpatePhone(String phone, long id);


}
