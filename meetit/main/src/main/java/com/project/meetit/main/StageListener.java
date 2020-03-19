package com.project.meetit.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class StageListener implements ApplicationListener<MeetitApp.StageReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(StageListener.class);

    private final String applicationTitle;
    private final Resource fxml;
    private final Resource style;
    private final ApplicationContext applicationContext;

    public StageListener(@Value("${spring.application.ui.title}") String applicationTitle,
                         @Value("classpath:/fxml/main.fxml") Resource fxml,
                         @Value("classpath:/styles/style.css") Resource style, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.fxml = fxml;
        this.style = style;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(MeetitApp.StageReadyEvent stageReadyEvent) {
        try {
            log.info("Starting Hello JavaFX and Maven demonstration application");

            Stage stage = stageReadyEvent.getStage();
            URL url = this.fxml.getURL();
            log.debug("Loading FXML for main view from: {}", url.toString());
            URL styleUrl = this.style.getURL();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load(getClass().getResourceAsStream("/fxml/main.fxml"));
            // Parent root = fxmlLoader.load();
            log.debug("Showing JFX scene");
            Scene scene = new Scene(root, 600, 600);
            scene.getStylesheets().add(styleUrl.toString());
            stage.setTitle("Hello JavaFX and Maven");
            stage.setScene(scene);
            stage.setTitle(this.applicationTitle);
            stage.show();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
