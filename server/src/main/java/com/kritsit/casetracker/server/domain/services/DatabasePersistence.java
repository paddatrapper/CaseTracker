package com.kritsit.casetracker.server.domain.services;

public class DatabasePersistence implements IPersistenceService {
    private boolean connected;

    public DatabasePersistence() {
        connected = false;
    }

    public boolean open() {
        return connected;
    }

    public boolean isOpen() {
        return connected;
    }

    public void close() {
        connected = false;
    }
}
