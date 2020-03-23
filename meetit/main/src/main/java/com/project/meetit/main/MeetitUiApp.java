package com.project.meetit.main;

import com.project.meetit.core.event.StageReadyEvent;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan({"com.project.meetit.core","com.project.meetit.dboperations"})
@Configuration
@EnableMongoRepositories(basePackages={"com.project.meetit.dboperations.repository"})
public class MeetitUiApp extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() throws Exception {
        ApplicationContextInitializer<GenericApplicationContext> initializer = genericApplicationContext -> {
            genericApplicationContext.registerBean(Application.class, () -> MeetitUiApp.this);
            genericApplicationContext.registerBean(Parameters.class, this::getParameters);
            genericApplicationContext.registerBean(HostServices.class, this::getHostServices);
        };
        this.springContext = new SpringApplicationBuilder().sources(MeetitApp.class).initializers(initializer).run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void stop() throws Exception {
        this.springContext.close();
        Platform.exit();
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.springContext.publishEvent(new StageReadyEvent(stage));
    }
}
