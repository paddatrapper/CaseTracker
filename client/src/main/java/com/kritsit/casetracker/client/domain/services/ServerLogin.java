package com.kritsit.casetracker.client.domain.services;

public class ServerLogin implements ILoginService {
    private IConnectionService connection;
    private String username;

    public ServerLogin(IConnectionService connection) {
        this.connection = connection;
    }

    public String getUsername() {
        return username;
    }
}
