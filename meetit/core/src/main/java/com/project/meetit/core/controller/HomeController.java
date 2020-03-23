package com.project.meetit.core.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @FXML
    private TextField homeField;

    @FXML
    private Label infoLabel;


    public void initialize()
    {
        LOGGER.info("HELLO");
    }

    public void createMeeting() {
        infoLabel.setText("Created a Meeting !");
    }
}
