package com.kritsit.casetracker.client.domain.ui.controller;

import com.kritsit.casetracker.client.domain.services.ILoginService;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements IController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @FXML private TextField txtUsername;
    @FXML private PasswordField pwdPassword;
    private ILoginService loginService;
    private Stage stage;
    private Staff user = null;

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Staff getUser() {
        return user;
    }
    
    @FXML protected void handleLoginButtonAction(ActionEvent e) {
        String username = txtUsername.getText();
        String password = pwdPassword.getText();
        login(username, password);
    }

    @FXML protected void handleCancelButtonAction(ActionEvent e) {
        logger.info("Close - user cancelled log in");
        stage.close();
    }

    private void login(String username, String password) {
        logger.info("Atempting to log in in user {}", username);
        if (loginService == null) {
            Alert failedLogin = new Alert(AlertType.ERROR);
            failedLogin.setTitle("Error");
            failedLogin.setHeaderText(null);
            failedLogin.setContentText("An error occured. The application will now close");
            failedLogin.showAndWait();
            logger.error("Unable to login - LoginService not defined");
            throw new RuntimeException("LoginService not defined");
        }
        if (loginService.login(username, password)) {
            logger.debug("User {} logged in", username);
            user = loginService.getUser(username, password);
            stage.close();
        } else {
            Alert failedLogin = new Alert(AlertType.WARNING);
            failedLogin.setTitle("Incorrect Login");
            failedLogin.setHeaderText(null);
            failedLogin.setContentText("Username and/or password incorrect. Unable to log into the server");
            failedLogin.showAndWait();
        }
    }
}
