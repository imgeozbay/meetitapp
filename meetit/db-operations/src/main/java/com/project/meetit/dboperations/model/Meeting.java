package com.project.meetit.dboperations.model;

import java.util.Date;
import java.util.List;

public class Meeting extends GenericModel {

    private String subject;
    private Date date;
    private MeetingRoom meetingRoom;
    private List<User> attendeeList;

    public Meeting(int id, String subject, Date date, MeetingRoom meetingRoom, List<User> attendeeList) {
        super(id);
        this.subject = subject;
        this.date = date;
        this.meetingRoom = meetingRoom;
        this.attendeeList = attendeeList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public List<User> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(List<User> attendeeList) {
        this.attendeeList = attendeeList;
    }
}
