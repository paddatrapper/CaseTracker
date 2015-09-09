package com.kritsit.casetracker.client.domain.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;

public final class LoadingDialog {
    private final Logger logger = LoggerFactory.getLogger(LoadingDialog.class);
    private Stage stage;
    private Parent root;

    public void run() throws IOException {
        stage = new Stage(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        logger.info("Starting splash screen");
        root = loadFXML("/ui/fxml/LoadingDialog.fxml");
        show();
    }

    private Parent loadFXML(String resourceURL) throws IOException {
        logger.debug("Loading FXML");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceURL));
        Parent root = loader.load();
        logger.debug("FXML loaded");
        return root;
    }

    private void show() {
        stage.setTitle("Loading...");
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }

    public void exit() {
        FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), root);
        fadeSplash.setFromValue(1.0);
        fadeSplash.setToValue(0.0);
        fadeSplash.setOnFinished(actionEvent -> stage.close());
        fadeSplash.play();
    }
}
