package com.project.meetit.core.logic;

import com.project.meetit.core.view.AlertDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DialogManager {

    public static void showAlertDialog(Alert.AlertType alertType, String text)
    {
        new AlertDialog(alertType, text);
    }

    public static boolean showConfirmationDialog(String text)
    {
        AlertDialog alertDialog = new AlertDialog(Alert.AlertType.CONFIRMATION, text);
        Optional<ButtonType> result = alertDialog.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK))
        {
            return true;
        }
        return false;
    }
}
