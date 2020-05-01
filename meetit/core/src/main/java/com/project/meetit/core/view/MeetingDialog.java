package com.project.meetit.core.view;

import com.project.meetit.core.component.DateTimePicker;
import com.project.meetit.core.util.CRUDOperationTypeEnum;
import com.project.meetit.dboperations.model.Meeting;
import com.project.meetit.dboperations.model.MeetingRoom;
import com.project.meetit.dboperations.model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;

public class MeetingDialog extends Dialog<List<Meeting>> {

    private CRUDOperationTypeEnum crudOperationTypeEnum;

    private TextField txtSubject;
    private DateTimePicker dpDateTimeStart;
    private DateTimePicker dpDateTimeEnd;
    private ComboBox<MeetingRoom> cmbMeetingRoom;
    private ListView<User> listViewAttendees;
    private CheckBox chkRecurrentMeeting;

    private ButtonType btnSave;
    private ButtonType btnCancel;

    public MeetingDialog(CRUDOperationTypeEnum crudOperationTypeEnum) {
        super();
        this.crudOperationTypeEnum = crudOperationTypeEnum;
        initialize();
    }

    private void initialize() {
        initView();
        setTitle(crudOperationTypeEnum == CRUDOperationTypeEnum.ADD ? "Create Meeting" : "Update Meeting");
        setResizable(true);
    }

    private void initView() {
        Label lblSubject = new Label("Meeting Subject:");
        Label lblStartDate = new Label("Start Date:");
        Label lblEndDate = new Label("End Date:");
        Label lblMeetingRoom = new Label("Meeting Room:");
        Label lblAttendeeList = new Label("Attendee List:");
        Label lblRecurrentMeeting = new Label("Is Recurrent Meeting?");

        txtSubject = new TextField();
        dpDateTimeStart = new DateTimePicker();
        dpDateTimeEnd = new DateTimePicker();
        cmbMeetingRoom = new ComboBox<>();
        listViewAttendees = new ListView<>();
        listViewAttendees.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        chkRecurrentMeeting = new CheckBox();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(lblSubject, 1, 1);
        grid.add(txtSubject, 2, 1);
        grid.add(lblStartDate, 1, 2);
        grid.add(dpDateTimeStart, 2, 2);
        grid.add(lblEndDate, 1, 3);
        grid.add(dpDateTimeEnd, 2, 3);
        grid.add(lblMeetingRoom, 1, 4);
        grid.add(cmbMeetingRoom, 2, 4);
        grid.add(lblAttendeeList, 1, 5);
        grid.add(listViewAttendees, 2, 5);
        if (crudOperationTypeEnum != CRUDOperationTypeEnum.UPDATE) {
            grid.add(lblRecurrentMeeting, 1, 6);
            grid.add(chkRecurrentMeeting, 2, 6);
        }
        getDialogPane().setContent(grid);

        btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        btnSave = new ButtonType("Save", ButtonBar.ButtonData.APPLY);

        getDialogPane().getButtonTypes().add(btnCancel);
        getDialogPane().getButtonTypes().add(btnSave);
    }

    public TextField getTxtSubject() {
        return txtSubject;
    }

    public DateTimePicker getDpDateTimeStart() {
        return dpDateTimeStart;
    }

    public DateTimePicker getDpDateTimeEnd() {
        return dpDateTimeEnd;
    }

    public ComboBox<MeetingRoom> getCmbMeetingRoom() {
        return cmbMeetingRoom;
    }

    public ListView<User> getListViewAttendees() {
        return listViewAttendees;
    }

    public CheckBox getChkRecurrentMeeting() {
        return chkRecurrentMeeting;
    }

    public ButtonType getBtnSave() {
        return btnSave;
    }

    public ButtonType getBtnCancel() {
        return btnCancel;
    }
}
