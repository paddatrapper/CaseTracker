package com.kritsit.casetracker.client.domain.ui.controller;

import com.kritsit.casetracker.client.domain.services.ILoginService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements IController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @FXML private TextField txtUsername;
    @FXML private PasswordField pwdPassword;
    private ILoginService loginService;
    
    @FXML protected void handleLoginButtonAction(ActionEvent e) {
        String username = txtUsername.getText();
        String password = pwdPassword.getText();
        login(username, password);
    }

    @FXML protected void handleCancelButtonAction(ActionEvent e) {
        logger.info("Close - user cancelled log in");
    }

    private void login(String username, String password) {
        logger.info("Atempting to log in in user {}", username);
    }
}
