package com.project.meetit.dboperations.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User extends GenericModel {

    @Value("${full}")
    private String fullName;
    @Value("${user}")
    private String userName;

    public User(int id, String fullName, String userName) {
        super(id);
        this.fullName = fullName;
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
