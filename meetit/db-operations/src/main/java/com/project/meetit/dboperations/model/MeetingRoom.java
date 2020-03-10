package com.project.meetit.dboperations.model;

public class MeetingRoom extends GenericModel {

    private String name;

    public MeetingRoom(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
