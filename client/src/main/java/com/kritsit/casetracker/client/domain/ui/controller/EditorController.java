package com.kritsit.casetracker.client.domain.ui.controller;

import com.kritsit.casetracker.client.domain.services.IEditorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class EditorController implements IController {
    private final Logger logger = LoggerFactory.getLogger(EditorController.class);
    private IEditorService editorService;
    private Stage stage;

    public void setEditorService(IEditorService editorService) {
        this.editorService = editorService;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML protected void handleFilterClearAction(ActionEvent e) {
    }

    @FXML protected void handleSummaryEditAction(ActionEvent e) {
    }

    @FXML protected void handleCalendarPreviousAction(ActionEvent e) {
    }

    @FXML protected void handleCalendarNextAction(ActionEvent e) {
    }

    @FXML protected void handleCalendarTodayAction(ActionEvent e) {
    }

    @FXML protected void handleAddNewDefendantAction(ActionEvent e) {
    }

    @FXML protected void handleAddNewComplainantAction(ActionEvent e) {
    }

    @FXML protected void handleAddEvidenceAction(ActionEvent e) {
    }

    @FXML protected void handleEditEvidenceAction(ActionEvent e) {
    }

    @FXML protected void handleDeleteEvidenceAction(ActionEvent e) {
    }

    @FXML protected void handleAddCaseAction(ActionEvent e) {
    }
}
