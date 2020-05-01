package com.project.meetit.core.event;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class LogoutEvent extends ApplicationEvent {

    public Stage getStage() {
        return Stage.class.cast(getSource());
    }

    public LogoutEvent(Stage source) {
        super(source);
    }
}
