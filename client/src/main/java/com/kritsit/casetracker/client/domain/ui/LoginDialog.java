package com.kritsit.casetracker.client.domain.ui;

import com.kritsit.casetracker.client.domain.factory.ServiceFactory;
import com.kritsit.casetracker.client.domain.services.ILoginService;
import com.kritsit.casetracker.client.domain.ui.controller.LoginController;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public final class LoginDialog {
    private final Logger logger = LoggerFactory.getLogger(LoginDialog.class);

    public Staff run(Stage stage) throws IOException {
        logger.info("Starting the login interface");
        logger.debug("Loading FXML");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/LoginDialog.fxml"));
        Parent root = loader.load();
        logger.debug("FXML loaded");
        LoginController controller = (LoginController) loader.getController();
        ILoginService loginService = ServiceFactory.getLoginService();
        controller.setLoginService(loginService);
        controller.setStage(stage);
        logger.debug("Login interface prepared");
        stage.setTitle("CaseTracker - Login");
        stage.setScene(new Scene(root, 300, 275));
        stage.showAndWait();
        return controller.getUser();
    }
}
