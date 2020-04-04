package com.project.meetit.dboperations.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
public class User {

    @Id
    private String _id;

    private String username;
    private String pswd;
    private String firstName;
    private String lastName;
    private String department;

    public String get_id() {
        return _id;
    }
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
        return this.firstName + " " + this.lastName;
    }
}
