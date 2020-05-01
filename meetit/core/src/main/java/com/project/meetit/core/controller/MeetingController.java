package com.project.meetit.core.controller;

import com.project.meetit.core.logic.DialogManager;
import com.project.meetit.core.util.CRUDOperationTypeEnum;
import com.project.meetit.core.util.helper.DateHelper;
import com.project.meetit.core.view.MeetingDialog;
import com.project.meetit.dboperations.model.Meeting;
import com.project.meetit.dboperations.model.MeetingRoom;
import com.project.meetit.dboperations.model.User;
import com.project.meetit.dboperations.repository.MeetingRepository;
import com.project.meetit.dboperations.repository.MeetingRoomRepository;
import com.project.meetit.dboperations.repository.UserRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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

    @NonNull
    private final DialogManager dialogManager;

    private CRUDOperationTypeEnum crudOperationTypeEnum;

    private MeetingDialog meetingDialog;
    private Meeting meeting;

    public void initialize(CRUDOperationTypeEnum crudOperationTypeEnum, Meeting meeting) {
        this.crudOperationTypeEnum = crudOperationTypeEnum;
        this.meeting = meeting;
        meetingDialog = new MeetingDialog(crudOperationTypeEnum);
        initializeDbOperations();
        if (crudOperationTypeEnum == CRUDOperationTypeEnum.UPDATE && meeting != null) {
            updateModel();
        }
        addListeners();
    }

    private void initializeDbOperations() {
        List<MeetingRoom> meetingRoomsDBList = meetingRoomRepository.findAll();
        meetingRoomsDBList.forEach(meetingRoom -> meetingDialog.getCmbMeetingRoom().getItems().add(meetingRoom));
        meetingDialog.getCmbMeetingRoom().getSelectionModel().selectFirst();

        //get users from mongo db
        List<User> attendeeDBList = userRepository.findAll();
        attendeeDBList.forEach(userObj -> meetingDialog.getListViewAttendees().getItems().add(userObj));
    }


    private void updateModel() {
        meetingDialog.getTxtSubject().setText(meeting.getSubject());
        meetingDialog.getDpDateTimeStart().setDateTimeValue(DateHelper.convertDateToLocalDateTime(meeting.getStartDate()));
        meetingDialog.getDpDateTimeEnd().setDateTimeValue(DateHelper.convertDateToLocalDateTime(meeting.getEndDate()));
        meeting.getAttendeeList().forEach(attendee -> meetingDialog.getListViewAttendees().getSelectionModel().select(attendee));
        meetingDialog.getCmbMeetingRoom().getSelectionModel().select(meeting.getMeetingRoom());
    }

    private void addListeners() {
        meetingDialog.setResultConverter(new Callback<ButtonType, List<Meeting>>() {
            @Override
            public List<Meeting> call(ButtonType b) {
                if (b == meetingDialog.getBtnSave()) {
                    String subject = meetingDialog.getTxtSubject().getText();
                    LocalDateTime startDate = meetingDialog.getDpDateTimeStart().getDateTimeValue();
                    LocalDateTime endDate = meetingDialog.getDpDateTimeEnd().getDateTimeValue();
                    MeetingRoom meetingRoom = meetingDialog.getCmbMeetingRoom().getSelectionModel().getSelectedItem();
                    ObservableList<User> selectedAttendeesIndices = meetingDialog.getListViewAttendees().getSelectionModel().getSelectedItems();
                    boolean recurrentMeeting = meetingDialog.getChkRecurrentMeeting().isSelected();
                    List<Meeting> meetingList = new ArrayList<>();

                    List<User> selectedAttendeeList = new ArrayList<>();
                    for (Object attendee : selectedAttendeesIndices) {
                        selectedAttendeeList.add((User) attendee);
                    }
                    if (crudOperationTypeEnum == CRUDOperationTypeEnum.UPDATE && meeting != null) {
                        meeting.setSubject(subject);
                        meeting.setStartDate(DateHelper.convertLocalDateTimeToDate(startDate));
                        meeting.setEndDate(DateHelper.convertLocalDateTimeToDate(endDate));
                        meeting.setMeetingroom(meetingRoom);
                        meeting.setAttendeelist(selectedAttendeeList);
                        meetingList.add(meeting);
                        meetingDialog.close();
                        return meetingList;
                    } else {
                        meetingDialog.close();
                        Meeting newMeeting = new Meeting(subject, DateHelper.convertLocalDateTimeToDate(startDate),
                                DateHelper.convertLocalDateTimeToDate(endDate), meetingRoom, selectedAttendeeList);
                        meetingList.add(newMeeting);
                        if (recurrentMeeting) {
                            for (int i = 1; i < 4; i++) {
                                try {
                                    Meeting meetingTemp = (Meeting) newMeeting.clone();
                                    meetingTemp.setStartDate(DateHelper.convertLocalDateTimeToDate(startDate.plusWeeks(i)));
                                    meetingTemp.setEndDate(DateHelper.convertLocalDateTimeToDate(endDate.plusWeeks(i)));
                                    meetingList.add(meetingTemp);
                                } catch (CloneNotSupportedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        return meetingList;
                    }
                } else if (b == meetingDialog.getBtnCancel()) {
                    meetingDialog.close();
                }

                return null;
            }
        });
        final Button btnSave = (Button) meetingDialog.getDialogPane().lookupButton(meetingDialog.getBtnSave());
        btnSave.addEventFilter(ActionEvent.ACTION, event -> {
                    if (isFormValid()) {
                        if (isDateTimeValid()) {
                            if (!isDateTimeValidForCurrent()) {
                                event.consume();
                                dialogManager.showAlertDialog(Alert.AlertType.ERROR, "Start Time cannot be less than Current Time");
                            }
                        } else {
                            event.consume();
                            dialogManager.showAlertDialog(Alert.AlertType.ERROR, "Start Date cannot be greater than End Date");
                        }

                    } else {
                        event.consume();
                        dialogManager.showAlertDialog(Alert.AlertType.ERROR, "Please fill out the mandatory fields. (Subject/Start Date/End Date)");
                    }
                }
        );
        meetingDialog.getDpDateTimeStart().valueProperty().addListener((ov, oldValue, newValue) -> meetingDialog.getDpDateTimeEnd().setValue(newValue));

        meetingDialog.getChkRecurrentMeeting().selectedProperty().addListener((ov, oldValue, newValue) -> {
            if (meetingDialog.getChkRecurrentMeeting().isSelected()) {
                dialogManager.showAlertDialog(Alert.AlertType.INFORMATION, "Recurrent event will be added for 4 weeks period.");
            }
        });
    }

    private boolean isFormValid() {
        boolean formValid = true;
        if ((meetingDialog.getTxtSubject().getText().isEmpty())
                || (meetingDialog.getDpDateTimeStart().getValue() == null)
                || (meetingDialog.getDpDateTimeEnd().getValue() == null)) {
            formValid = false;
        }
        return formValid;
    }

    private boolean isDateTimeValid() {
        return meetingDialog.getDpDateTimeStart().getDateTimeValue().isBefore(meetingDialog.getDpDateTimeEnd().getDateTimeValue());
    }

    private boolean isDateTimeValidForCurrent() {
        return meetingDialog.getDpDateTimeStart().getDateTimeValue().isAfter(LocalDateTime.now());
    }

    public Optional<List<Meeting>> showDialogGetResult() {
        return meetingDialog.showAndWait();
    }


}
