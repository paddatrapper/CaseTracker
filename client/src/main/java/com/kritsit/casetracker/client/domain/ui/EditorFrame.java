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

    public void run(Staff user, Stage stage) throws IOException {
        logger.info("Starting the editor's user interface");
        logger.debug("Loading FXML...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/LoginDialog.fxml"));
        Parent root = loader.load();
        logger.debug("FXML loaded");
        EditorController controller = (EditorController) loader.getController();
        IEditorService editorService = ServiceFactory.getEditorService(user);
        controller.setEditorService(editorService);
        controller.setStage(stage);
        logger.debug("Editor's user interface prepared");
        stage.setTitle("CaseTracker");
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }
}
