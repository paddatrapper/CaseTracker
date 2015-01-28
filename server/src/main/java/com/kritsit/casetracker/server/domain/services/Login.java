package com.kritsit.casetracker.server.domain.services;

import java.util.Arrays;

public class LoginService implements ILoginService {
    private IPersistenceService persistence;

    public LoginService(IPersistenceService persistence) {
        this.persistence = persistence;
    }

    public boolean login(String username, int passwordHash) {
        long passwordSaltedHash = persistence.getPasswordSaltedHash(username);
        long salt = persistence.getSalt(username);
        long testPasswordSaltedHash = salt + passwordHash;
        return !(passwordSaltedHash == -1 || testPasswordSaltedHash != passwordSaltedHash);
    }
}
