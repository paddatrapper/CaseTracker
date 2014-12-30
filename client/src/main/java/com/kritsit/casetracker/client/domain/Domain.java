package com.kritsit.casetracker.client.domain;

import com.kritsit.casetracker.client.domain.services.IConnectionService;
import com.kritsit.casetracker.client.domain.services.ServerConnection;

public class Domain {
    private static IConnectionService connection;

    public static IConnectionService getServerConnection() {
        if (connection == null) {
            connection = new ServerConnection();
        }
        return connection;
    }

    public static IConnectionService getConnection() {
        return connection;
    }

    public static void resetServerConnection() {
        if (connection instanceof ServerConnection) {
            connection = null;
        }
    }
}
