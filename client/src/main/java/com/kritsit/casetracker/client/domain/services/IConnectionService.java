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
    List<Staff> getStaff();
    String getLastCaseNumber();
    boolean addCase(Case c);
    boolean editCase(Case c);
    boolean addUser(Staff s);
    boolean editUser(Staff s);
    boolean deleteUser(String username);
    boolean resetPassword(String username, int hashedRandomPass);
    boolean changePassword(String username, int currentHashedPass, 
            int newHashedPass);
    boolean checkForUpdate(String currentVersion);
    byte[] getUpdate();
}
