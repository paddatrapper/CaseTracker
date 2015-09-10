package com.kritsit.casetracker.client.domain.ui.controller;

import com.kritsit.casetracker.client.domain.services.IEditorService;
import com.kritsit.casetracker.client.domain.services.InputToModelParseResult;
import com.kritsit.casetracker.client.domain.ui.LoadingDialog;
import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Evidence;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EditCaseController {
    private final Logger logger = LoggerFactory.getLogger(EditCaseController.class);
    private Case c;
    private IEditorService editorService;
    private EditorController controller;
    private Stage stage = null;
    
    public EditCaseController(Case c, IEditorService editorService, EditorController controller){
        this.c = c;
        this.editorService = editorService;
        this.controller = controller;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void initialize(){
        logger.info("Initiating");
        ObservableList<Staff> inspectors = FXCollections.observableArrayList(
                editorService.getInspectors());
        cmbInvestigatingOfficer.setItems(inspectors);
        if (editorService.getUser().getPermission() == Permission.EDITOR) {
            cmbInvestigatingOfficer.setValue(editorService.getUser());
        }

        ObservableList<String> caseTypes = FXCollections.observableArrayList(
                editorService.getCaseTypes());
        cmbCaseType.setItems(caseTypes);

        cbxIsReturnVisit.selectedProperty().addListener((obs, oldValue, newValue) -> {
                dpkReturnDate.setDisable(!newValue);
        });

        txfAddress.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                txfLongitude.setDisable(false);
                txfLatitude.setDisable(false);
            } else {
                txfLongitude.setDisable(true);
                txfLatitude.setDisable(true);
            }
        });

        txfLongitude.textProperty().addListener((obs, oldValue, newValue) -> {
            String latitude = txfLatitude.getText();
            if (newValue == null || newValue.isEmpty()) {
                if (latitude == null || latitude.isEmpty()) {
                    txfAddress.setDisable(false);
                }
            } else {
                txfAddress.setDisable(true);
            }
        });

        txfLatitude.textProperty().addListener((obs, oldValue, newValue) -> {
            String longitude = txfLongitude.getText();
            if (newValue == null || newValue.isEmpty()) {
                if (longitude == null || longitude.isEmpty()) {
                    txfAddress.setDisable(false);
                }
            } else {
                txfAddress.setDisable(true);
            }
        });

        updateButton.setOnAction(event->{
            updateCase();
        });

        populateFields();
    }

    private void populateFields() {
        txfCaseNumber.setText(c.getNumber());
        dpkIncidentDate.setValue(c.getIncident().getDate());
        cmbInvestigatingOfficer.setValue(editorService.getUser());
        cmbCaseType.setValue(c.getType());
        cbxIsReturnVisit.setSelected(c.isReturnVisit());
        dpkReturnDate.setValue(c.getReturnDate());
        txfCaseName.setText(c.getName());
        cmbComplainant.getItems().add(c.getComplainant());
        cmbComplainant.setValue(c.getComplainant());
        cmbDefendant.getItems().add(c.getDefendant());
        cmbDefendant.setValue(c.getDefendant());
        txfAddress.setText(c.getIncident().getAddress());
        txfLongitude.setText("" + c.getIncident().getLongitude());
        txfLatitude.setText("" + c.getIncident().getLatitude());
        txfRegion.setText(c.getIncident().getRegion());
        txaDetails.setText(c.getDescription());
        txaAnimalsInvolved.setText(c.getAnimalsInvolved());
        lstEvidence.setItems(FXCollections.observableList(c.getEvidence()));
    }

    @FXML protected void handleAddEvidenceAction(ActionEvent e) {
        logger.info("Adding evidence to new case");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add Evidence Files");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt", "*.docx", "*.xmlx", "*.doc", "*.xml", "*.pdf"),
                new ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif", "*.jpeg"),
                new ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mkv", "*.mts"),
                new ExtensionFilter("All Files", "*.*"));
        File evidenceFile = fileChooser.showOpenDialog(stage);
        if (evidenceFile != null) {
            logger.debug("Adding selected evidence to new case");
            String name = evidenceFile.getName();
            Evidence evidence = new Evidence(-1, name, null, evidenceFile);
            lstEvidence.getItems().add(evidence);
        }
    }

    @FXML protected void handleEditEvidenceAction(ActionEvent e) {
        logger.info("Editing evidence attached to new case");
        Evidence evidence = lstEvidence.getSelectionModel().getSelectedItem();
        Evidence oldEvidence = evidence;
        if (evidence != null) {
            TextInputDialog editDialog = new TextInputDialog(evidence.getDescription());
            editDialog.setTitle("Edit Evidence");
            editDialog.setContentText("Please enter the description:");
            Optional<String> newDescription = editDialog.showAndWait();
            if (newDescription.isPresent()) {
                logger.debug("Setting new evidence description to {}", newDescription.get());
                evidence.setDescription(newDescription.get());
                int index = lstEvidence.getItems().indexOf(oldEvidence);
                lstEvidence.getItems().set(index, evidence);
            }
        } else {
            logger.debug("No evidence selected to edit");
            Alert selectionWarning = new Alert(AlertType.WARNING);
            selectionWarning.setTitle("No Evidence Selected");
            selectionWarning.setContentText("No evidence selected to edit");
            selectionWarning.showAndWait();
        }
    }

    @FXML protected void handleDeleteEvidenceAction(ActionEvent e) {
        logger.info("Deleting evidence from new case");
        Evidence evidence = lstEvidence.getSelectionModel().getSelectedItem();
        if (evidence != null) {
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setContentText("Are you sure you want to remove this evidence?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                logger.debug("Deleting evidence {} from new case", evidence);
                lstEvidence.getItems().remove(evidence);
            }
        } else {
            logger.debug("No evidence selected to delete");
            Alert selectionWarning = new Alert(AlertType.WARNING);
            selectionWarning.setTitle("No Evidence Selected");
            selectionWarning.setContentText("No evidence selected to delete");
            selectionWarning.showAndWait();
        }
    }
    
    private void updateCase() {
        logger.info("Creating new case");
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.run();
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("caseNumber", txfCaseNumber.getText());
        inputMap.put("incidentDate", dpkIncidentDate.getValue());
        inputMap.put("investigatingOfficer", cmbInvestigatingOfficer
                .getSelectionModel().getSelectedItem());
        inputMap.put("caseType", cmbCaseType.getSelectionModel().getSelectedItem()); 
        inputMap.put("isReturnVisit", cbxIsReturnVisit.isSelected());
        inputMap.put("returnDate", dpkReturnDate.getValue());
        inputMap.put("caseName", txfCaseName.getText());
        inputMap.put("defendant", cmbDefendant.getSelectionModel().getSelectedItem());
        inputMap.put("complainant", cmbComplainant.getSelectionModel().getSelectedItem());
        inputMap.put("address", txfAddress.getText());
        inputMap.put("longitude", txfLongitude.getText());
        inputMap.put("latitude", txfLatitude.getText());
        inputMap.put("region", txfRegion.getText());
        inputMap.put("details", txaDetails.getText());
        inputMap.put("animalsInvolved", txaAnimalsInvolved.getText());
        inputMap.put("evidence", lstEvidence.getItems());

        InputToModelParseResult result = editorService.editCase(inputMap);
        loadingDialog.exit();

        if(result.isSuccessful()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Editing case");
            alert.setHeaderText("Case " + txfCaseNumber.getText() + " edited successfully");
            alert.setContentText("Click OK to proceed");
            controller.refreshCaseList();
            alert.showAndWait();
        } else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error editing case " + txfCaseNumber.getText());
            alert.setContentText(result.getReason());
            alert.showAndWait();
        }
    }
    
    @FXML private Button updateButton;
    @FXML private CheckBox cbxIsReturnVisit;
    @FXML private ComboBox<Person> cmbComplainant;
    @FXML private ComboBox<Defendant> cmbDefendant;
    @FXML private ComboBox<Staff> cmbInvestigatingOfficer;
    @FXML private ComboBox<String> cmbCaseType;
    @FXML private DatePicker dpkIncidentDate;
    @FXML private DatePicker dpkReturnDate;
    @FXML private ListView<Evidence> lstEvidence;
    @FXML private TextArea txaAnimalsInvolved;
    @FXML private TextArea txaDetails;
    @FXML private TextField txfAddress;
    @FXML private TextField txfCaseName;
    @FXML private TextField txfCaseNumber;
    @FXML private TextField txfLatitude;
    @FXML private TextField txfLongitude;
    @FXML private TextField txfRegion;
}
