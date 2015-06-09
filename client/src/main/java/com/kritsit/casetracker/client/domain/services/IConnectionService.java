package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Staff;

import java.io.IOException;
import java.util.List;

public interface IConnectionService {
    boolean open(String host, int port) throws IllegalArgumentException;
    boolean isOpen();
    void close() throws IOException;
    boolean login(String username, int hash);
    Staff getUser(String username, int hash);
    List<Case> getCases(Staff user);
    List<Staff> getInspectors();
    String getLastCaseNumber();
}
