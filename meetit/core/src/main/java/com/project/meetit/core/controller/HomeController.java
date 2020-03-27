package com.project.meetit.core.controller;

import com.project.meetit.core.util.helper.DateHelper;
import com.project.meetit.dboperations.model.Meeting;
import com.project.meetit.dboperations.model.MeetingRoom;
import com.project.meetit.dboperations.model.User;
import com.project.meetit.dboperations.repository.MeetingRepository;
import com.project.meetit.dboperations.repository.MeetingRoomRepository;
import com.project.meetit.dboperations.repository.UserRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@Data
@RequestMapping("meeting")
@RequiredArgsConstructor
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @FXML
    private TextField subjectField;
    @FXML
    private DatePicker dateTimeField;
    @FXML
    private ComboBox<MeetingRoom> meetingRoomList;
    @FXML
    private ListView<User> attendeesList;
    @FXML
    private Button addAttendees;

    @NonNull
    private final MeetingRoomRepository meetingRoomRepository;
    @NonNull
    private final MeetingRepository meetingRepository;
    @NonNull
    private final UserRepository userRepository;

    @FXML
    private Label infoLabel;

    public void initialize()
    {
        LOGGER.info("HELLO");
        //get meeting rooms from mongo db
        List<MeetingRoom> meetingRoomsDBList = meetingRoomRepository.findAll();
        meetingRoomsDBList.forEach(meetingRoom -> meetingRoomList.getItems().add(meetingRoom));

        //get users from mongo db
        attendeesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<User> attendeeDBList = userRepository.findAll();
        attendeeDBList.forEach(userObj -> attendeesList.getItems().add(userObj));


    }

    public void createMeeting() {
        //meetingRoomRepository.getCollection()

        String subject = subjectField.getText();
        LocalDate dateTime = dateTimeField.getValue();
        MeetingRoom meetingRoom = meetingRoomList.getSelectionModel().getSelectedItem();
        ObservableList<User> selectedAttendeesIndices = attendeesList.getSelectionModel().getSelectedItems();

        List<User> selectedAttendeeList = new ArrayList<>();
        for(Object attendee : selectedAttendeesIndices){

            selectedAttendeeList.add((User)attendee);
        }

        Meeting meeting = new Meeting(subject, DateHelper.convertLocalDateToDate(dateTime), meetingRoom, selectedAttendeeList);

        meetingRepository.insert(meeting);
        List<Meeting> meetingTemp = meetingRepository.findAll();


        meetingTemp.forEach(meeting1 -> System.out.println(meeting1));
        infoLabel.setText("Created a Meeting !");
    }
}
