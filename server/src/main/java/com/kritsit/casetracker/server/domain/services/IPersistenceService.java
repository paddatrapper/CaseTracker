package com.kritsit.casetracker.server.domain.services;

public interface IPersistenceService {
    boolean open();
    boolean isOpen();
    void close();
}
