package com.kritsit.casetracker.client.domain.services;

public interface IMenuService {
    void changePasswordFrame();
    int changePassword(String username, int currentHashedPass, int newHashedPass);
    void updateFrame();
    void closeConnection();
    void restart();
}
