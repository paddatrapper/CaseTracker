package com.kritsit.casetracker.client.domain.ui;

import com.kritsit.casetracker.shared.domain.model.Staff;

import javafx.stage.Stage;
import java.io.IOException;

public interface IUserInterface {
    void run(Staff user, Stage stage) throws IOException;
}
