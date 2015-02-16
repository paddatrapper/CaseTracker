package com.kritsit.casetracker.client.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ServerLogin implements ILoginService {
    private final Logger logger = LoggerFactory.getLogger(ServerLogin.class);
    private IConnectionService connection;

    public ServerLogin(IConnectionService connection) {
        this.connection = connection;
    }

    public boolean login(String username, char[] charPassword) {
        if (!connection.isOpen()) {
            logger.warn("Connection closed, unable to log in");
            return false;
        }
        String password = new String(charPassword);
        Arrays.fill(charPassword, '0');
        logger.debug("Logging in");
        return connection.login(username, password.hashCode());
    }
}
