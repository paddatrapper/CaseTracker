package com.kritsit.casetracker.client;

import javafx.application.Application;
import javafx.stage.Stage;

public class CaseTrackerClient extends Application {
    private static final String VERSION = "0.1a";

    public static void main(String[] args) {
        Application.launch(args);
    }

    public String getVersion() {
        return VERSION;
    }

    @Override
    public void start(Stage stage) {
        //TODO: Show Login Frame
    }
}
