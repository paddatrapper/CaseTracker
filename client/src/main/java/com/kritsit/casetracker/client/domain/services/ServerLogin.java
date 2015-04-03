package com.kritsit.casetracker.client.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerLogin implements ILoginService {
    private final Logger logger = LoggerFactory.getLogger(ServerLogin.class);
    private IConnectionService connection;

    public ServerLogin(IConnectionService connection) {
        this.connection = connection;
    }

    public boolean login(String username, String password) {
        if (!connection.isOpen()) {
            logger.warn("Connection closed, unable to log in");
            return false;
        }
        logger.debug("Logging in");
        return connection.login(username, password.hashCode());
    }
}
