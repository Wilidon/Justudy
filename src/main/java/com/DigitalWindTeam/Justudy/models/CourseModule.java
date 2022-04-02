package com.DigitalWindTeam.Justudy.models;

import javax.persistence.*;
import java.util.List;


@Entity
@Table()
public class CourseModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "level_name")
    private String levelName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "module_id", referencedColumnName = "id")
    private List<CourseModuleHeader> courseModuleHeaders;

    public CourseModule() {
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

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public List<CourseModuleHeader> getCourseModuleHeaders() {
        return courseModuleHeaders;
    }

    public void setCourseModuleHeaders(List<CourseModuleHeader> courseModuleHeaders) {
        this.courseModuleHeaders = courseModuleHeaders;
    }
}
