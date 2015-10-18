package com.kritsit.casetracker.client.domain.ui.controller;

import java.util.HashMap;
import java.util.Map;

import com.kritsit.casetracker.client.domain.services.IAdministratorService;
import com.kritsit.casetracker.client.domain.services.InputToModelParseResult;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditUserController {

    private Staff s;
    private IAdministratorService administratorService;
    private AdministratorController c;
    
    public EditUserController(Staff s, IAdministratorService a, AdministratorController c){
        this.s = s;
        administratorService = a;
        this.c=c;
    }
    
    public void initialize(){
        
        initPermissionCombobox();
        
        firstNameField.setText(s.getFirstName());
        lastNameField.setText(s.getLastName());
        usernameField.setText(s.getUsername());
        positionField.setText(s.getPosition());
        departmentCombobox.setValue(s.getDepartment());
        permissionCombobox.setValue(s.getPermission());
        
        updateButton.setOnAction(event->{
            updateUser();
        });
    }
    
    private void initPermissionCombobox(){
        ObservableList<Permission> permissions = 
                FXCollections.observableArrayList(Permission.ADMIN,
                Permission.EDITOR, Permission.VIEWER);
        permissionCombobox.setItems(permissions);
    }
    
    private void updateUser(){
        Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("username", usernameField.getText());
        inputMap.put("firstname", firstNameField.getText());
        inputMap.put("lastname", lastNameField.getText());
        inputMap.put("department", departmentCombobox.getValue());
        inputMap.put("position", positionField.getText());
        inputMap.put("permission", permissionCombobox.getValue().toString());
        
        InputToModelParseResult result = administratorService.editUser(inputMap);
        if(result.isSuccessful()){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Editing user");
        alert.setHeaderText("User "+usernameField.getText()+" edited successfully");
        alert.setContentText("Click OK to proceed");
        c.initStaffTable();
        alert.showAndWait();
        }
        else{
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error editing user "+usernameField.getText());
        alert.setContentText(result.getReason());
        alert.showAndWait();
        }
    }
    
    @FXML private Button updateButton;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private TextField positionField;
    @FXML private ComboBox<Permission> permissionCombobox;
    @FXML private ComboBox<String> departmentCombobox;
    
}
