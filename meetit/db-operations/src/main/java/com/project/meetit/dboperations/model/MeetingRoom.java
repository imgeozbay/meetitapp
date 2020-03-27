package com.project.meetit.dboperations.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "meetingroom")
@Data
public class MeetingRoom {

    @Id
    private String _id;

    private String name;

    public String getName() {
        return name;
    }

    public String toString() {
        return this.name;
    }
}

