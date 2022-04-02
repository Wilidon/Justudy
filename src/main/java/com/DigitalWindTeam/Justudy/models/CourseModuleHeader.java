package com.DigitalWindTeam.Justudy.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table()
public class CourseModuleHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "header_id", referencedColumnName = "id")
    private List<CourseModuleHeaderStep> courseModuleHeaderSteps;

    public CourseModuleHeader() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CourseModuleHeaderStep> getCourseModuleHeaderSteps() {
        return courseModuleHeaderSteps;
    }

    public void setCourseModuleHeaderSteps(List<CourseModuleHeaderStep> courseModuleHeaderSteps) {
        this.courseModuleHeaderSteps = courseModuleHeaderSteps;
    }
}
