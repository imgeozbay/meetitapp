package com.project.meetit.dboperations;

import com.project.meetit.dboperations.model.GenericModel;
import com.project.meetit.dboperations.model.Meeting;
import com.project.meetit.dboperations.model.MeetingRoom;
import com.project.meetit.dboperations.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetingDAO implements IMeetitDAO {

//    private JdbcTemplate template;
//
//    public void setTemplate(JdbcTemplate template) {
//        this.template = template;
//    }

    @Override
    public boolean create(GenericModel genericModel) {

        MeetingRowMapper meetingRowMapper = new MeetingRowMapper();

        return false;
    }

    @Override
    public boolean update(int id, GenericModel genericModel) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<GenericModel> list() {
        return null;
    }

    private class MeetingRowMapper {

        public Meeting convertToMeeting(){

            MeetingRoom meetingRoom = new MeetingRoom(-1, "");
            List<User> attendees = new ArrayList<>();
            attendees.add(new User(-1, "", ""));

            // Fill attributes and return
            return new Meeting(-1, "", new Date(), meetingRoom, attendees);
        }


    }
}
