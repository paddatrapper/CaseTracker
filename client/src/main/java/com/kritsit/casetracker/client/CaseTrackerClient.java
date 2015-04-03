package com.kritsit.casetracker.client;

import com.kritsit.casetracker.client.domain.ui.IUserInterface;
import com.kritsit.casetracker.client.domain.ui.LoginDialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class CaseTrackerClient extends Application {
    private static final String VERSION = "0.1a";
    private final Logger logger = LoggerFactory.getLogger(CaseTrackerClient.class);

    public static void main(String[] args) {
        Application.launch(args);
    }

    public String getVersion() {
        return VERSION;
    }

    @Override
    public void start(Stage stage) throws Exception {
        IUserInterface loginDialog = new LoginDialog();
        try {
            loginDialog.start(stage);
        } catch(IOException e) {
            logger.error("Unable to start the login dialog", e);
            throw e;
        }
    }
}
