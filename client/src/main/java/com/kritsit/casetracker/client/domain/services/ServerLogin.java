package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.Configuration;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerLogin implements ILoginService {
    private final Logger logger = LoggerFactory.getLogger(ServerLogin.class);
    private IConnectionService connection;

    public ServerLogin(IConnectionService connection) {
        this.connection = connection;
    }

    public boolean login(String username, String password) {
        if (!openConnection()) {
            return false;
        }
        logger.debug("Logging in");
        return connection.login(username, password.hashCode());
    }

    public Staff getUser(String username, String password) {
        if (!openConnection()) {
            return null;
        }
        logger.debug("Getting user");
        return connection.getUser(username, password.hashCode());
    }

    private boolean openConnection() {
        if (!connection.isOpen()) {
            if (!connection.open(Configuration.getServer(), Configuration.getPort())) {
                logger.warn("Unable to open connection with server");
                return false;
            }
        }
        return true;
    }
}
