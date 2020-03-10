package com.project.meetit.main;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeetitUiApp {

    public static void main(String[] args) {
        Application.launch(MeetitApp.class, args);
    }
}
