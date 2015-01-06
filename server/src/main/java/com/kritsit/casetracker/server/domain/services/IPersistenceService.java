package com.kritsit.casetracker.server.domain.services;

import java.util.Map;

public interface IPersistenceService {
    boolean open();
    boolean isOpen();
    long getPasswordSaltedHash(String user);
    long getSalt(String user);
    Map<String, String> getUserDetails(String username);
    void close();
}
