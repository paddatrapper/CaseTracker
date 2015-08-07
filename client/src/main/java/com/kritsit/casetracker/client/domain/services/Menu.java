package com.kritsit.casetracker.client.domain.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kritsit.casetracker.client.domain.ui.controller.ChangePasswordController;
import com.kritsit.casetracker.shared.domain.model.Staff;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Menu implements IMenuService {
    
    private final Logger logger = LoggerFactory.getLogger(Menu.class);
    private Staff user;
    private IConnectionService connectionService;
    
    public Menu(Staff user, IConnectionService connectionService){
        this.user=user;
        this.connectionService=connectionService;
    }

    public void changePasswordFrame() {
        
        ChangePasswordController c = new ChangePasswordController(user, this);
        AnchorPane ChangePasswordPane = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/ui/fxml/ChangePasswordFrame.fxml"));
        
        fxmlLoader.setController(c);
        fxmlLoader.setRoot(ChangePasswordPane);
        
        try{
            ChangePasswordPane= (AnchorPane) fxmlLoader.load();
        }
        catch(IOException e){
            logger.error("Error loading frame to change password " + e);
            return;
        }
        
        Scene scene = new Scene(ChangePasswordPane);
        Stage stage = new Stage();
        stage.setTitle("Changing password for "+user.getUsername());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        
    }

    public int changePassword(String username, int currentHashedPass, int newHashedPass) {
        logger.info("Changing password for user {}", username);
        boolean result = connectionService.changePassword(username, currentHashedPass, newHashedPass);
        if(result){
            logger.info("Password changed succesfully");
            return 200;
        }
        else{
            logger.error("Error changing user's password");
            return 500;
        }
    }

}
