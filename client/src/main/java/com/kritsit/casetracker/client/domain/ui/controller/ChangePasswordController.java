package com.kritsit.casetracker.client.domain.ui.controller;

import com.kritsit.casetracker.client.domain.services.IMenuService;
import com.kritsit.casetracker.shared.domain.model.Staff;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class ChangePasswordController {
    
    private Staff user;
    private IMenuService menuService;
    
    public ChangePasswordController(Staff user, IMenuService menuService){
        this.user=user;
        this.menuService=menuService;
    }
    
    public void initialize(){
        changePasswordButton.setOnAction(event->{
            changePassword(this.user, this.menuService);
        });
    }

    private void changePassword(Staff user, IMenuService menuService){
        
    }
    
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField repeatField;
    @FXML private Button changePasswordButton;
}
