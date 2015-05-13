package com.kritsit.casetracker.client.domain.ui;

import com.kritsit.casetracker.client.domain.factory.ServiceFactory;
import com.kritsit.casetracker.client.domain.services.IEditorService;
import com.kritsit.casetracker.client.domain.ui.controller.EditorController;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class EditorFrame implements IUserInterface {
    private static final Logger logger = LoggerFactory.getLogger(EditorFrame.class);
    private EditorController controller;
    private Staff user;
    private Stage stage;

    public void run(Staff user, Stage stage) throws IOException {
        this.user = user;
        this.stage = stage;
        logger.info("Starting the editor's user interface");
        Parent root = loadFXML("/ui/fxml/EditorFrame.fxml");
        setUpController();
        this.stage.setTitle("CaseTracker");
        this.stage.setScene(new Scene(root, 1200, 600));
        this.stage.showAndWait();
    }

    private Parent loadFXML(String resourceURL) throws IOException {
        logger.debug("Loading FXML");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceURL));
        Parent root = loader.load();
        controller = (EditorController) loader.getController();
        logger.debug("FXML loaded");
        return root;
    }

    private void setUpController() {
        logger.debug("Setting up controller");
        IEditorService editorService = ServiceFactory.getEditorService(user);
        controller.setEditorService(editorService);
        controller.setStage(stage);
        controller.populateTables(); 
        logger.debug("Controller set up");
    }
}
