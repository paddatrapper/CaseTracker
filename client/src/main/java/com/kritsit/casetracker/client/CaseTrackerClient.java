package com.kritsit.casetracker.client;

public class CaseTrackerClient {

    private static final String VERSION = "0.1a";

    public CaseTrackerClient(String[] args) {
    
    }

    public String getVersion() {
        return VERSION;
    }

    public static void main(String[] args) {
        new CaseTrackerClient(args);
    }
}
