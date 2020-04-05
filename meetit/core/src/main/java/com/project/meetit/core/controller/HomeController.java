package com.project.meetit.core.controller;

import com.project.meetit.core.logic.DialogManager;
import com.project.meetit.core.util.CRUDOperationTypeEnum;
import com.project.meetit.dboperations.model.Meeting;
import com.project.meetit.dboperations.model.User;
import com.project.meetit.dboperations.repository.MeetingRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@Component
@RestController
@Data
@RequestMapping("meeting")
@RequiredArgsConstructor
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @FXML
    private TableView<Meeting> meetingTable;
    @FXML
    public TableColumn<Meeting, String> subjectCol;
    @FXML
    public TableColumn<Meeting, Date> dateCol;
    @FXML
    public TableColumn<Meeting, String> meetingRoomCol;
    @FXML
    public TableColumn<Meeting, String> attendeesCol;

    @NonNull
    private final MeetingRepository meetingRepository;
    @NonNull
    private final MeetingController meetingController;
    @NonNull
    private final DialogManager dialogManager;

    private ObservableList<Meeting> tableMeetingDataList;

    public void initialize() {
        LOGGER.info("HELLO");

        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        meetingRoomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeetingRoom().getName()));
        attendeesCol.setCellValueFactory(cellData -> {
            StringBuilder attendees = new StringBuilder();
            Meeting meeting = cellData.getValue();
            for (int i = 0; i < meeting.getAttendeeList().size(); i++) {
                User attendee = meeting.getAttendeeList().get(i);
                if (i == meeting.getAttendeeList().size() - 1) {
                    attendees.append(attendee.getFirstName() + " " + attendee.getLastName());
                } else {
                    attendees.append(attendee.getFirstName() + " " + attendee.getLastName() + ", ");
                }
            }
            return new SimpleStringProperty(attendees.toString());
        });

        List<Meeting> meetingList = meetingRepository.findAll();
        tableMeetingDataList = FXCollections.observableArrayList(meetingList);
        meetingTable.setItems(tableMeetingDataList);
    }

    public void createMeeting() {
        meetingController.initialize(CRUDOperationTypeEnum.ADD, null);
        Optional<Meeting> meeting = meetingController.showDialogGetResult();
        if (meeting.isPresent()) {
            Meeting newMeeting = meetingRepository.insert(meeting.get());
            if (newMeeting != null) {
                tableMeetingDataList.add(newMeeting);
            }
        }
    }

    public void updateMeeting() {
        Meeting selectedMeeting = meetingTable.getSelectionModel().getSelectedItem();
        if (selectedMeeting != null) {
            meetingController.initialize(CRUDOperationTypeEnum.UPDATE, selectedMeeting);
            Optional<Meeting> meeting = meetingController.showDialogGetResult();
            if (meeting.isPresent()) {
                meetingRepository.save(meeting.get());
                meetingTable.refresh();
            }
        } else {
            dialogManager.showAlertDialog(Alert.AlertType.INFORMATION, "Please select a meeting.");
        }
    }

    public void deleteMeeting() {
        Meeting selectedMeeting = meetingTable.getSelectionModel().getSelectedItem();
        if (selectedMeeting != null) {
            if (DialogManager.showConfirmationDialog("Proceed deleting the selected meeting?")) {
                meetingRepository.delete(selectedMeeting);
                tableMeetingDataList.remove(selectedMeeting);
                meetingTable.refresh();
            }
        } else {
            dialogManager.showAlertDialog(Alert.AlertType.INFORMATION, "Please select a meeting.");
        }
    }
}
