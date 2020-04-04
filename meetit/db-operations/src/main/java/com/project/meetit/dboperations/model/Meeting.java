package com.project.meetit.dboperations.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(collection = "meeting")
@Data
public class Meeting {

    @Id
    private String _id;

    private String subject;
    private Date date;
    private MeetingRoom meetingroom;
    private List<User> attendeelist;

    public Meeting(String subject, Date date, MeetingRoom meetingroom, List<User> attendeelist) {
        this.subject = subject;
        this.date = date;
        this.meetingroom = meetingroom;
        this.attendeelist = attendeelist;
    }

    public String get_id() {
        return _id;
    }

    public String getSubject() {
        return subject;
    }

    public Date getDate() {
        return date;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingroom;
    }

    public List<User> getAttendeeList() {
        return attendeelist;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMeetingroom(MeetingRoom meetingroom) {
        this.meetingroom = meetingroom;
    }

    public void setAttendeelist(List<User> attendeelist) {
        this.attendeelist = attendeelist;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "subject='" + subject + '\'' +
                ", date=" + date +
                ", meetingroom=" + meetingroom +
                ", attendeelist=" + attendeelist +
                '}';
    }
}
