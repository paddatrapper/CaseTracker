package com.kritsit.casetracker.client.domain.ui;

import com.kritsit.casetracker.client.domain.Domain;
import com.kritsit.casetracker.client.domain.services.ILoginService;
import com.kritsit.casetracker.client.domain.ui.controller.LoginController;
import com.kritsit.casetracker.shared.domain.model.Staff;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginDialog {
    public Staff run(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/LoginDialog.fxml"));
        Parent root = loader.load();
        LoginController controller = (LoginController) loader.getController();
        ILoginService loginService = Domain.getLoginService();
        controller.setLoginService(loginService);
        controller.setStage(stage);
        stage.setTitle("CaseTracker - Login");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
        return controller.getUser();
    }
}
