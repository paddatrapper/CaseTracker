package com.kritsit.casetracker.client.domain.ui.controller;

import com.kritsit.casetracker.client.CaseTrackerClient;
import com.kritsit.casetracker.client.domain.services.IUpdateService;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class UpdateController implements IController {
    private final Logger logger = LoggerFactory.getLogger(UpdateController.class);
    private IUpdateService updater;
    private File client = null;
    private Stage stage;

    public void setUpdateService(IUpdateService updater) {
        this.updater = updater;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setStatus(String status) {
        txaStatus.appendText(status + System.lineSeparator());
    }

    @FXML
    protected void handleUpdateAction() {
        if (btnUpdate.isDisabled()) {
            return;
        }
        switch (btnUpdate.getText()) {
            case "Check for update" :
            case "Check for Update" :
                checkForUpdate();
                break;
            case "Update" :
                updateClient();
                break;
            default :  // Restart
                restart();
                break;
        }
    }

    @FXML
    protected void handleCancelAction() {
        if (btnUpdate.isDisabled() && 
                "Check for update".equals(btnUpdate.getText())) {
            return;
        }
        stage.close();
    }

    private void checkForUpdate() {
        boolean updateRequired = updater.checkForUpdate(CaseTrackerClient.getVersion());
        if (updateRequired) {
            setStatus("Client out of date. Update required");
            btnUpdate.setText("Update");
        } else {
            setStatus("Client up to date");
            btnUpdate.setDisable(true);
        }
    }

    private void updateClient() {
        try {
            btnUpdate.setDisable(true);
            client = updater.update();
            setStatus("Client updated, restart required.");
            btnUpdate.setText("Restart");
            btnUpdate.setDisable(false);
        } catch (IOException ex) {
            logger.error("Unable to download update", ex);
            setStatus("An error occurred. Unable to download update. " + ex.getMessage());
            btnUpdate.setDisable(false);
            btnUpdate.setText("Check for update");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Update");
            alert.setHeaderText("Error during update");
            alert.setContentText("The update could not be downloaded: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    private void restart() {
        if (client == null) {
            logger.warn("Trying to restart for update that failed to download");
            setStatus("Unable to restart as the update could not be downloaded");
            btnUpdate.setText("Check for update");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Restart");
            alert.setHeaderText("Error during restart");
            alert.setContentText("Unable to restart as the update could not be downloaded");
            alert.showAndWait();
        }
        try {
            btnUpdate.setDisable(true);
            updater.launch(client);
        } catch (IOException ex) {
            logger.error("Unable to restart.", ex);
            setStatus("An error occurred. Unable to restart: " + ex.getMessage());
            btnUpdate.setDisable(false);
            btnUpdate.setText("Check for update");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Restart");
            alert.setHeaderText("Error during restart");
            alert.setContentText("An error occurred. Unable to restart: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML private Button btnUpdate;
    @FXML private TextArea txaStatus;
}
