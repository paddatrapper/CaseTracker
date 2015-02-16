package com.kritsit.casetracker.client.domain;

import com.kritsit.casetracker.client.domain.services.IConnectionService;
import com.kritsit.casetracker.client.domain.services.ServerConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Domain {
    private static IConnectionService connection;
    private static final Logger logger = LoggerFactory.getLogger(Domain.class);

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
}
