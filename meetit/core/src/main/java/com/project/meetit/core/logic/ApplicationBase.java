package com.project.meetit.core.logic;

import com.project.meetit.dboperations.model.User;
import javafx.stage.Stage;

public class ApplicationBase {

    private static ApplicationBase applicationBase;

    private User currentUser;
    private Stage stage;

    private ApplicationBase()
    {

    }

    public static ApplicationBase getInstance()
    {
        if (applicationBase == null)
        {
            applicationBase = new ApplicationBase();
        }
        return applicationBase;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
