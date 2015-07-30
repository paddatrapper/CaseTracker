package com.kritsit.casetracker.client.domain.ui.controller;

import com.kritsit.casetracker.client.domain.services.IAdministratorService;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Staff;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditUserController {

    private Staff s;
    private IAdministratorService administratorService;
    
    public EditUserController(Staff s, IAdministratorService a){
        this.s = s;
        administratorService = a;
    }
    
    public void initialize(){
        
        initPermissionCombobox();
        
        firstNameField.setText(s.getFirstName());
        lastNameField.setText(s.getLastName());
        usernameField.setText(s.getUsername());
        positionField.setText(s.getPosition());
        departmentCombobox.setValue(s.getDepartment());
        permissionCombobox.setValue(s.getPermission());
    }
    
    private void initPermissionCombobox(){
        ObservableList<Permission> permissions = 
                FXCollections.observableArrayList(Permission.ADMIN,
                Permission.EDITOR, Permission.VIEWER);
        permissionCombobox.setItems(permissions);
    }
    
    @FXML private Button updateButton;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private TextField positionField;
    @FXML private ComboBox<Permission> permissionCombobox;
    @FXML private ComboBox<String> departmentCombobox;
    
}
