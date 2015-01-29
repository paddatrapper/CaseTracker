package com.kritsit.casetracker.server.domain;

import com.kritsit.casetracker.server.datalayer.DatabasePersistence;
import com.kritsit.casetracker.server.datalayer.IPersistenceService;

public class Domain {
    private static IPersistenceService persistence;

    public static IPersistenceService getPersistenceService() {
        if (persistence == null) {
            persistence = new DatabasePersistence();
        }
        return persistence;
    }

    public static void resetPersistenceService() {
        persistence = null;
    }

    public static String getDbHostName() {
        return "kritsit.ddns.net";
    }

    public static int getDbPort() {
        return 3306;
    }

    public static String getDbSchema() {
        return "CaseTracker";
    }

    public static String getDbUsername() {
        return "CaseTracker";
    }

    public static String getDbPassword() {
        return "casetracker";
    }
}
