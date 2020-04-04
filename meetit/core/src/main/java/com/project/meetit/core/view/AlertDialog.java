package com.project.meetit.core.view;

import javafx.scene.control.Alert;

public class AlertDialog extends Alert {
    public AlertDialog(AlertType alertType, String text) {
        super(alertType);
        switch (alertType)
        {
            case CONFIRMATION:
                setTitle("Confirmation");
                setContentText(text);
                break;
            case INFORMATION:
                setTitle("Information");
                setHeaderText(text);
                show();
                break;
            case ERROR:
                setTitle("Error");
                setHeaderText(text);
                show();
                break;
        }
    }
}
