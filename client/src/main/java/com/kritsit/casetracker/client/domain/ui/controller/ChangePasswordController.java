package com.kritsit.casetracker.client.domain.ui.controller;

import com.kritsit.casetracker.client.domain.services.IMenuService;
import com.kritsit.casetracker.shared.domain.model.Staff;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;

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
        Alert alert;
        if(currentPasswordField.getText().equals("")||newPasswordField.getText().equals("")){
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Changing password");
            alert.setHeaderText("Information");
            alert.setContentText("Fill all empty fields");
            alert.showAndWait();   
            return;
        }
        if(!newPasswordField.getText().equals(repeatField.getText())){
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Changing password");
            alert.setHeaderText("Information");
            alert.setContentText("Passwords don't match");
            alert.showAndWait();   
            return;
        }
        int currentHashedPass = currentPasswordField.getText().hashCode();
        int newHashedPass = newPasswordField.getText().hashCode();
        int result = menuService.changePassword(user.getUsername(), currentHashedPass, newHashedPass);
        switch(result){
        case 200 :
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Changing password");
            alert.setHeaderText("Password changed succesfully");
            alert.setContentText("Click OK to proceed");
            alert.showAndWait();
            break;
        
        case 500 :
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Changing password");
            alert.setHeaderText("Error while changing password");
            alert.setContentText("Error on the server side");
            alert.showAndWait();
            break;
          }
    }
    
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField repeatField;
    @FXML private Button changePasswordButton;
}
