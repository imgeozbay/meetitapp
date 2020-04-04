package com.project.meetit.core.controller;

import com.project.meetit.core.event.AppReadyEvent;
import com.project.meetit.core.event.StageReadyEvent;
import com.project.meetit.core.logic.ApplicationBase;
import com.project.meetit.dboperations.model.User;
import com.project.meetit.dboperations.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("user")
@RequiredArgsConstructor
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @FXML
    private TextField usernameField;
    @FXML
    private TextField pswdField;
    @FXML
    private Label infoLabel;

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final ApplicationContext applicationContext;

    @FXML
    public void initialize() {
        usernameField.setText("orkunonurgil");
        pswdField.setText("123");
    }

    public void login() {
        String username = usernameField.getText();
        String pswd = pswdField.getText();

        StringBuilder builder = new StringBuilder();

//        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(pswd)) {
//            builder.append("Please Enter Username and Password");
//        }

        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getPswd().equals(pswd)) {
                ApplicationBase.getInstance().setCurrentUser(user);
                applicationContext.publishEvent(new AppReadyEvent(ApplicationBase.getInstance().getStage()));
                builder.append("Logged in !!!");
            } else {
                builder.append("Incorrect Password !!!");
            }
        } else {
            builder.append("User is not found ! " + username);
        }

        if (builder.length() > 0) {
            String info = builder.toString();
            infoLabel.setText(info);
        } else {
            infoLabel.setText("Please Enter Username and Password");
        }
    }


}

