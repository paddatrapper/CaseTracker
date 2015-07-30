package com.kritsit.casetracker.client.domain.ui.controller;

import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Staff;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditUserController {

    private Staff s;
    
    public EditUserController(Staff s){
        this.s=s;
    }
    
    @FXML private Button updateButton;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField username;
    @FXML private TextField position;
    @FXML private ComboBox<Permission> permissionCombobox;
    @FXML private ComboBox<String> departmentCombobox;
    
}
