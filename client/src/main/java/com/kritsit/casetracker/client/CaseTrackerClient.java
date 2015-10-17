package com.kritsit.casetracker.client;

import com.kritsit.casetracker.client.domain.factory.UserInterfaceFactory;
import com.kritsit.casetracker.client.domain.ui.IUserInterface;
import com.kritsit.casetracker.client.domain.ui.LoginDialog;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class CaseTrackerClient extends Application {
    private static final String VERSION = "0.2.1-ALPHA";
    private static final Logger logger = LoggerFactory.getLogger(CaseTrackerClient.class);

    public static void main(String[] args) {
        if (args.length == 0) {
            Application.launch(args);
            return;
        }
        if (args[0].equals("-v") || args[0].equals("--version")) {
            logger.debug("Printing version information");
            logger.info("CaseTracker Client (GPLv3)\nVersion: " + getVersion());
            System.out.println("CaseTracker Client (GPLv3)\nVersion: " + getVersion());
            System.exit(0);
        } else {
            Application.launch(args);
        }
    }

    public static String getVersion() {
        return VERSION;
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginDialog loginDialog = new LoginDialog();
        try {
            logger.debug("Opening log in prompt");
            Stage visible = new Stage();
            Staff user = loginDialog.run(visible);
            if (user != null) {
                logger.info("User logged in");
                launchUI(user, visible);
            }
        } catch(IOException e) {
            logger.error("Unable to start the login dialog", e);
            throw e;
        }
    }

    private void launchUI(Staff user, Stage stage) throws IOException {
        IUserInterface ui = null;
        switch (user.getPermission()) {
            case ADMIN: logger.debug("User admin. Opening administrator frame");
                        ui = UserInterfaceFactory.getAdministratorFrame(); 
                        break;
            case EDITOR: 
            case VIEWER:
                        logger.debug("User editor/viewer. Opening editor frame");
                        ui = UserInterfaceFactory.getEditorFrame(); 
                        break;
        }
        if(ui != null){
            ui.run(user, stage);
        }
    }
}
