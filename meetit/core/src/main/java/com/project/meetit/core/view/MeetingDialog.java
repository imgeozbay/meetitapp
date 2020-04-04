package com.project.meetit.core.view;

import com.project.meetit.core.util.CRUDOperationTypeEnum;
import com.project.meetit.dboperations.model.Meeting;
import com.project.meetit.dboperations.model.MeetingRoom;
import com.project.meetit.dboperations.model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class MeetingDialog extends Dialog<Meeting> {

    private CRUDOperationTypeEnum crudOperationTypeEnum;

    private TextField txtSubject;
    private DatePicker dpDate;
    private ComboBox<MeetingRoom> cmbMeetingRoom;
    private ListView<User> listViewAttendees;

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
        Label lblDate = new Label("Date:");
        Label lblMeetingRoom = new Label("Meeting Room:");
        Label lblAttendeeList = new Label("Attendee List:");

        txtSubject = new TextField();
        dpDate = new DatePicker();
        cmbMeetingRoom = new ComboBox<>();
        listViewAttendees = new ListView<>();
        listViewAttendees.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(lblSubject, 1, 1);
        grid.add(txtSubject, 2, 1);
        grid.add(lblDate, 1, 2);
        grid.add(dpDate, 2, 2);
        grid.add(lblMeetingRoom, 1, 3);
        grid.add(cmbMeetingRoom, 2, 3);
        grid.add(lblAttendeeList, 1, 4);
        grid.add(listViewAttendees, 2, 4);
        getDialogPane().setContent(grid);

        btnCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        btnSave = new ButtonType("Save", ButtonBar.ButtonData.APPLY);

        getDialogPane().getButtonTypes().add(btnCancel);
        getDialogPane().getButtonTypes().add(btnSave);
    }

    public TextField getTxtSubject() {
        return txtSubject;
    }

    public DatePicker getDpDate() {
        return dpDate;
    }

    public ComboBox<MeetingRoom> getCmbMeetingRoom() {
        return cmbMeetingRoom;
    }

    public ListView<User> getListViewAttendees() {
        return listViewAttendees;
    }

    public ButtonType getBtnSave() {
        return btnSave;
    }

    public ButtonType getBtnCancel() {
        return btnCancel;
    }
}
