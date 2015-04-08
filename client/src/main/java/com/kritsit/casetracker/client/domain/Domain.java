package com.kritsit.casetracker.client.domain;

import com.kritsit.casetracker.client.domain.services.IConnectionService;
import com.kritsit.casetracker.client.domain.services.ILoginService;
import com.kritsit.casetracker.client.domain.services.ServerConnection;
import com.kritsit.casetracker.client.domain.services.ServerLogin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Domain {
    private static final Logger logger = LoggerFactory.getLogger(Domain.class);
    private static IConnectionService connection;

    public static IConnectionService getServerConnection() {
        if (connection == null) {
            logger.info("Creating new server connection");
            connection = new ServerConnection();
        }
        return connection;
    }

    public static void resetServerConnection() {
        logger.debug("Resetting server connection");
        connection = null;
    }

    public static ILoginService getLoginService() {
        logger.debug("Creating new login service");
        return new ServerLogin(getServerConnection());
    }

    public static String getServerAddress() {
        return "localhost";
    }

    public static int getServerConnectionPort() {
        return 1244;
    }
}
