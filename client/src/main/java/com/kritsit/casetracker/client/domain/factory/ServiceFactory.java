package com.kritsit.casetracker.client.domain.factory;

import com.kritsit.casetracker.client.domain.services.Editor;
import com.kritsit.casetracker.client.domain.services.IConnectionService;
import com.kritsit.casetracker.client.domain.services.IEditorService;
import com.kritsit.casetracker.client.domain.services.ILoginService;
import com.kritsit.casetracker.client.domain.services.ServerConnection;
import com.kritsit.casetracker.client.domain.services.ServerLogin;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);
    private static IConnectionService connection;

    public static synchronized IConnectionService getServerConnection() {
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

    public static IEditorService getEditorService(Staff user) {
        logger.debug("Creating new editor service");
        return new Editor(user, getServerConnection());
    }
}
