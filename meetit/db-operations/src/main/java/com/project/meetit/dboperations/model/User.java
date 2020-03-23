package com.project.meetit.dboperations.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
public class User {

    @Id
    private String id;

    private String username;
    private String pswd;
    private String firstName;
    private String lastName;
    private String department;

//    public User(String firstName, String lastName, String department) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.department = department;
//    }

    public String getUsername() {
        return username;
    }

    public String getPswd() {
        return pswd;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, username='%s', firstName='%s', lastName='%s'], department='%s']",
                id, username, firstName, lastName, department);
    }
}
