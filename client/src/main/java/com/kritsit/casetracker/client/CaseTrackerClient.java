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
            case ADMIN: throw new UnsupportedOperationException("Not yet implemented");
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
