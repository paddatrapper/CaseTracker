package com.kritsit.casetracker.client.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kritsit.casetracker.client.domain.ui.controller.ChangePasswordController;
import com.kritsit.casetracker.client.domain.ui.controller.UpdateController;
import com.kritsit.casetracker.shared.domain.model.Staff;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;

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
        
        try {
            ChangePasswordPane= (AnchorPane) fxmlLoader.load();
        } catch(IOException e) {
            logger.error("Error loading frame to change password.",  e);
            return;
        }
        
        Scene scene = new Scene(ChangePasswordPane);
        Stage stage = new Stage();
        stage.setTitle("Changing password for " + user.getUsername());
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

    public void updateFrame() {
        UpdateController controller = new UpdateController();
        IUpdateService updater = new Updater(connectionService, controller);
        controller.setUpdateService(updater);
        BorderPane updateFrame = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/ui/fxml/UpdateFrame.fxml"));
        
        fxmlLoader.setController(controller);
        fxmlLoader.setRoot(updateFrame);
        
        try {
            updateFrame = (BorderPane) fxmlLoader.load();
        } catch(IOException e) {
            logger.error("Error loading updater frame.",  e);
            return;
        }
        
        Scene scene = new Scene(updateFrame);
        Stage stage = new Stage();
        stage.setTitle("Update Checker");
        stage.setResizable(false);
        stage.setScene(scene);
        controller.setStage(stage);
        stage.showAndWait();
    }

    public void closeConnection() {
        try {
            connectionService.close();
        } catch (IOException e) {
            logger.error("Unable to close connection", e);
        }
    }
    
    public void restart() {
        closeConnection();
        try {
            String java = System.getProperty("java.home") + "/bin/java";
            List<String> vmArguments = ManagementFactory.getRuntimeMXBean()
                .getInputArguments();
            StringBuffer vmArgsOneLine = new StringBuffer();
            for (String arg : vmArguments) {
                if (!arg.contains("-agentlib")) {
                    vmArgsOneLine.append(arg);
                    vmArgsOneLine.append(" ");
                }
            }
            final StringBuffer cmd = new StringBuffer(java + " " + vmArgsOneLine);
            String[] mainCommand = System.getProperty("sun.java.command").split(" ");
            if (mainCommand[0].endsWith(".jar")) {
                cmd.append("-jar " + new File(mainCommand[0]).getPath());
            } else {
                cmd.append("-cp \"" + System.getProperty("java.class.path") 
                        + "\"" + mainCommand[0]);
            }
            for (int i = 1; i < mainCommand.length; i++) {
                cmd.append(" ");
                cmd.append(mainCommand[i]);
            }
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        Runtime.getRuntime().exec(cmd.toString());
                    } catch (IOException e) {
                        logger.error("Unable to start application", e);
                    }
                }
            });
            System.exit(0);
        } catch (Exception e) {
            logger.error("Unable to restart the application", e);
        }
    }
}
