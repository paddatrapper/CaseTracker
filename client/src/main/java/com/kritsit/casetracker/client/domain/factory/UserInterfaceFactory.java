package com.kritsit.casetracker.client.domain.factory;

import com.kritsit.casetracker.client.domain.ui.AdministratorFrame;
import com.kritsit.casetracker.client.domain.ui.EditorFrame;
import com.kritsit.casetracker.client.domain.ui.IUserInterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.stage.Stage;

public class UserInterfaceFactory {
    public static IUserInterface getEditorFrame() {
        return new EditorFrame();
    }
    
    public static IUserInterface getAdministratorFrame() {
        return new AdministratorFrame();
    }
}
