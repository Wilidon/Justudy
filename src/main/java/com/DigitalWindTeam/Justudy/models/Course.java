package com.DigitalWindTeam.Justudy.models;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table()
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "full_description")
    private String fullDescription;
    @Column(name = "for_whom_title_1")
    private String forWhomTitle1;
    @Column(name = "for_whom_title_2")
    private String forWhomTitle2;
    @Column(name = "for_whom_desc_1")
    private String forWhomDesc1;
    @Column(name = "for_whom_desc_2")
    private String forWhomDesc2;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "participants")
    private Integer participants;
    @Column(name = "link")
    private String link;
    @Column(name = "isHidden")
    private Boolean isHidden;

    @ApiModelProperty(hidden = true)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private List<CourseModule> modules;

    public Course() {

    }

    public Course(Long id, String title, String description, String fullDescription,
                  String forWhomTitle1, String forWhomTitle2, String forWhomDesc1,
                  String forWhomDesc2, Integer duration, Integer participants,
                  String link, Boolean isHidden) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.fullDescription = fullDescription;
        this.forWhomTitle1 = forWhomTitle1;
        this.forWhomTitle2 = forWhomTitle2;
        this.forWhomDesc1 = forWhomDesc1;
        this.forWhomDesc2 = forWhomDesc2;
        this.duration = duration;
        this.participants = participants;
        this.link = link;
        this.isHidden = isHidden;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getForWhomTitle1() {
        return forWhomTitle1;
    }

    public void setForWhomTitle1(String forWhomTitle1) {
        this.forWhomTitle1 = forWhomTitle1;
    }

    public String getForWhomTitle2() {
        return forWhomTitle2;
    }

    public void setForWhomTitle2(String forWhomTitle2) {
        this.forWhomTitle2 = forWhomTitle2;
    }

    public String getForWhomDesc1() {
        return forWhomDesc1;
    }

    public void setForWhomDesc1(String forWhomDesc1) {
        this.forWhomDesc1 = forWhomDesc1;
    }

    public String getForWhomDesc2() {
        return forWhomDesc2;
    }

    public void setForWhomDesc2(String forWhomDesc2) {
        this.forWhomDesc2 = forWhomDesc2;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public List<CourseModule> getModules() {
        return modules;
    }

    public void setModules(List<CourseModule> modules) {
        this.modules = modules;
    }
}
