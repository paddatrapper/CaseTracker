package com.kritsit.casetracker.client.domain.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginDialog implements IUserInterface {
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/LoginDialog.fxml"));
        stage.setTitle("CaseTracker - Login");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}
