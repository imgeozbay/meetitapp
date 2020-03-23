package com.project.meetit.core.event;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class AppReadyEvent extends ApplicationEvent {

    public Stage getStage() {
        return Stage.class.cast(getSource());
    }

    public AppReadyEvent(Stage source) {
        super(source);
    }
}
