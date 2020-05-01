package com.project.meetit.core.listener;

import com.project.meetit.core.event.StageReadyEvent;
import com.project.meetit.core.logic.ApplicationBase;
import com.project.meetit.core.util.helper.ResourceHelper;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class StageReadyEventListener implements ApplicationListener<StageReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(StageReadyEventListener.class);

    private final String applicationTitle;
    private final Resource fxml;
    private final Resource style;
    private final ApplicationContext applicationContext;

    public StageReadyEventListener(@Value("${spring.application.ui.title}") String applicationTitle,
                                   @Value("classpath:/fxml/main.fxml") Resource fxml,
                                   @Value("classpath:/styles/style.css") Resource style,
                                   ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.fxml = fxml;
        this.style = style;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        try {
            log.info("Staget Ready Event Listener Called");

            Stage stage = stageReadyEvent.getStage();
            // Parent root = fxmlLoader.load();
            log.debug("Showing JFX scene");
            URL styleUrl = this.style.getURL();
            Scene scene = new Scene(ResourceHelper.getInstance().getParentNode(fxml, applicationContext), 300, 300);
            scene.getStylesheets().add(styleUrl.toString());
            stage.setTitle("Hello JavaFX and Maven");
            stage.setScene(scene);
            stage.setTitle(this.applicationTitle);
            ApplicationBase.getInstance().setStage(stage);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
