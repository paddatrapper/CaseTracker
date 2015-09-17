package com.kritsit.casetracker.client.domain.ui.controller;

import java.util.HashMap;
import java.util.Map;

import com.kritsit.casetracker.client.domain.services.IEditorService;
import com.kritsit.casetracker.client.domain.services.InputToModelParseResult;
import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Person;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddPersonController {

    private IEditorService editorService;
    private boolean isDefendant;
    private Person person = null;
    private Stage stage;
    
    public AddPersonController(IEditorService editorService, boolean isDefendant) {
        this.editorService = editorService;
        this.isDefendant = isDefendant;
    }

    public void initialize() {
        btnOK.setOnAction(event -> {
            createPerson();
        });
        btnCancel.setOnAction(event -> {
            person = null;
            stage.close();
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void createPerson() {
        Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("id", txfId.getText());
        inputMap.put("firstName", txfFirstName.getText());
        inputMap.put("lastName", txfLastName.getText());
        inputMap.put("telephoneNumber", txfTelephoneNumber.getText());
        inputMap.put("address", txfAddress.getText());
        inputMap.put("emailAddress", txfEmailAddress.getText());
        
        InputToModelParseResult<Person> result = editorService.createPerson(inputMap);
        if (result.isSuccessful()) {
            person = result.getResult();
            stage.close();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            String name = txfFirstName + " " + txfLastName;
            if (isDefendant) {
                alert.setHeaderText("Error creating defendant " + name);
            } else {
                alert.setHeaderText("Error creating complainant " + name);
            }
            alert.setContentText(result.getReason());
            alert.showAndWait();
        }
    }

    public Person getComplainant() {
        return person;
    }

    public Defendant getDefendant() {
        if (person == null) {
            return null;
        }
        int indexId = person.getIndexId();
        String id = person.getId();
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String address = person.getAddress();
        String telephoneNumber = person.getTelephoneNumber();
        String emailAddress = person.getEmailAddress(); 
        Defendant defendant = new Defendant(indexId, id, firstName, lastName, 
                address, telephoneNumber, emailAddress, false);
        return defendant;
    }

    @FXML private Button btnCancel;
    @FXML private Button btnOK;
    @FXML private TextField txfId;
    @FXML private TextField txfFirstName;
    @FXML private TextField txfLastName;
    @FXML private TextField txfTelephoneNumber;
    @FXML private TextField txfAddress;
    @FXML private TextField txfEmailAddress;
}
