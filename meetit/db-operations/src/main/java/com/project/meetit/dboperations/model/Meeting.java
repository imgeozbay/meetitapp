package com.project.meetit.dboperations.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(collection = "meeting")
@Data
public class Meeting implements Cloneable {

    @Id
    private String _id;

    private String subject;
    private Date startDate;
    private Date endDate;
    private MeetingRoom meetingroom;
    private List<User> attendeelist;

    public Meeting(String subject, Date startDate, Date endDate, MeetingRoom meetingroom, List<User> attendeelist) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
        this.meetingroom = meetingroom;
        this.attendeelist = attendeelist;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String get_id() {
        return _id;
    }

    public String getSubject() {
        return subject;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
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

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", meetingroom=" + meetingroom +
                ", attendeelist=" + attendeelist +
                '}';
    }
}
