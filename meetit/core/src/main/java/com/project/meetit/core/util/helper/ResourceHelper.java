package com.project.meetit.core.util.helper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;

public class ResourceHelper {

    private static final Logger log = LoggerFactory.getLogger(ResourceHelper.class);

    private static ResourceHelper resourceHelper;

    private ResourceHelper()
    {

    }

    public static ResourceHelper getInstance()
    {
        if (resourceHelper == null)
        {
            resourceHelper = new ResourceHelper();
        }
        return resourceHelper;
    }

    public Parent getParentNode(Resource fxml, ApplicationContext applicationContext)
    {
        Parent root = null;
        try {
            URL url = fxml.getURL();
            log.debug("Loading FXML for main view from: {}", url.toString());

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            root = fxmlLoader.load(fxml.getInputStream());
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return root;
    }
}
