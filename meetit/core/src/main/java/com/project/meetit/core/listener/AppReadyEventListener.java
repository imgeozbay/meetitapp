package com.project.meetit.core.listener;

import com.project.meetit.core.event.AppReadyEvent;
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
public class AppReadyEventListener implements ApplicationListener<AppReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(AppReadyEventListener.class);

    private final Resource fxml;
    private final Resource style;
    private final ApplicationContext applicationContext;

    public AppReadyEventListener(@Value("classpath:/fxml/home.fxml") Resource fxml,
                                 @Value("classpath:/styles/style.css") Resource style,
                                 ApplicationContext applicationContext) {
        this.fxml = fxml;
        this.style = style;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(AppReadyEvent appReadyEvent) {
        try {
            log.info("Starting Hello JavaFX and Maven demonstration application");

            Stage stage = appReadyEvent.getStage();
            log.debug("Showing JFX scene");
            URL styleUrl = this.style.getURL();
            Scene scene = new Scene(ResourceHelper.getInstance().getParentNode(fxml, applicationContext), 1200, 400);
            scene.getStylesheets().add(styleUrl.toString());
            stage.setScene(scene);
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
