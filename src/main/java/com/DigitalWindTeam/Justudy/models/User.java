package com.DigitalWindTeam.Justudy.models;

import com.DigitalWindTeam.Justudy.controllers.v1.AuthController;
import com.fasterxml.jackson.annotation.JsonView;

public class User {
    @JsonView(AuthController.class)
    private int id;
    @JsonView(AuthController.class)
    private String name;
    @JsonView(AuthController.class)
    private String surname;
    @JsonView(AuthController.class)
    private String email;
    @JsonView(AuthController.class)
    private String phone;
    private String password;
    private String ip;
    @JsonView(AuthController.class)
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
