package com.project.meetit.core.controller;

import com.project.meetit.core.util.CRUDOperationTypeEnum;
import com.project.meetit.core.util.helper.DateHelper;
import com.project.meetit.core.view.MeetingDialog;
import com.project.meetit.dboperations.model.Meeting;
import com.project.meetit.dboperations.model.MeetingRoom;
import com.project.meetit.dboperations.model.User;
import com.project.meetit.dboperations.repository.MeetingRepository;
import com.project.meetit.dboperations.repository.MeetingRoomRepository;
import com.project.meetit.dboperations.repository.UserRepository;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RestController
@Data
@RequiredArgsConstructor
public class MeetingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeetingController.class);

    @NonNull
    private final MeetingRepository meetingRepository;
    @NonNull
    private final MeetingRoomRepository meetingRoomRepository;
    @NonNull
    private final UserRepository userRepository;

    private CRUDOperationTypeEnum crudOperationTypeEnum;

    private MeetingDialog meetingDialog;
    private Meeting meeting;

    public void initialize(CRUDOperationTypeEnum crudOperationTypeEnum, Meeting meeting)
    {
        this.crudOperationTypeEnum = crudOperationTypeEnum;
        this.meeting = meeting;
        meetingDialog = new MeetingDialog(crudOperationTypeEnum);
        initializeDbOperations();
        if (crudOperationTypeEnum == CRUDOperationTypeEnum.UPDATE && meeting != null)
        {
            updateModel();
        }
        addListeners();
    }

    private void initializeDbOperations() {
        List<MeetingRoom> meetingRoomsDBList = meetingRoomRepository.findAll();
        meetingRoomsDBList.forEach(meetingRoom -> meetingDialog.getCmbMeetingRoom().getItems().add(meetingRoom));

        //get users from mongo db
        List<User> attendeeDBList = userRepository.findAll();
        attendeeDBList.forEach(userObj -> meetingDialog.getListViewAttendees().getItems().add(userObj));
    }


    private void updateModel() {
        meetingDialog.getTxtSubject().setText(meeting.getSubject());
        meetingDialog.getDpDate().setValue(DateHelper.convertDateToLocalDate(meeting.getDate()));
        meeting.getAttendeeList().forEach(attendee -> meetingDialog.getListViewAttendees().getSelectionModel().select(attendee));
        meetingDialog.getCmbMeetingRoom().getSelectionModel().select(meeting.getMeetingRoom());
    }

    private void addListeners() {
        meetingDialog.setResultConverter(new Callback<ButtonType, Meeting>() {
            @Override
            public Meeting call(ButtonType b) {

                if (b == meetingDialog.getBtnSave()) {
                    String subject = meetingDialog.getTxtSubject().getText();
                    LocalDate dateTime = meetingDialog.getDpDate().getValue();
                    MeetingRoom meetingRoom = meetingDialog.getCmbMeetingRoom().getSelectionModel().getSelectedItem();
                    ObservableList<User> selectedAttendeesIndices = meetingDialog.getListViewAttendees().getSelectionModel().getSelectedItems();

                    List<User> selectedAttendeeList = new ArrayList<>();
                    for (Object attendee : selectedAttendeesIndices) {
                        selectedAttendeeList.add((User) attendee);
                    }
                    if (crudOperationTypeEnum == CRUDOperationTypeEnum.UPDATE && meeting != null)
                    {
                        meeting.setSubject(subject);
                        meeting.setDate(DateHelper.convertLocalDateToDate(dateTime));
                        meeting.setMeetingroom(meetingRoom);
                        meeting.setAttendeelist(selectedAttendeeList);
                        return meeting;
                    }
                    else {
                        return new Meeting(subject, DateHelper.convertLocalDateToDate(dateTime), meetingRoom, selectedAttendeeList);
                    }
                } else {
                    meetingDialog.close();
                }

                return null;
            }
        });
    }

    public Optional<Meeting> showDialogGetResult() {
        return meetingDialog.showAndWait();
    }


}
