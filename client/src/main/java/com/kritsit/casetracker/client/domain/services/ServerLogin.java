package com.kritsit.casetracker.client.domain.services;

import java.util.Arrays;

public class ServerLogin implements ILoginService {
    private IConnectionService connection;

    public ServerLogin(IConnectionService connection) {
        this.connection = connection;
    }

    public boolean login(String username, char[] password) {
        Arrays.fill(password, '0');
        return false;
    }
}
