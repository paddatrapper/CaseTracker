package com.kritsit.casetracker.client.domain.services;

import java.util.Arrays;

public class ServerLogin implements ILoginService {
    private IConnectionService connection;

    public ServerLogin(IConnectionService connection) {
        this.connection = connection;
    }

    public boolean login(String username, char[] charPassword) {
        if (!connection.isOpen()) {
            return false;
        }
        String password = new String(charPassword);
        Arrays.fill(charPassword, '0');
        return connection.login(username, password.hashCode());
    }
}
