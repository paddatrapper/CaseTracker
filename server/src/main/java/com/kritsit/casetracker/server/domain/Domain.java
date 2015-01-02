package com.kritsit.casetracker.server.domain;

import com.kritsit.casetracker.server.domain.services.DatabasePersistence;
import com.kritsit.casetracker.server.domain.services.IPersistenceService;

public class Domain {
    private static IPersistenceService persistence;

    public static IPersistenceService getPersistenceService() {
        if (persistence == null) {
            persistence = new DatabasePersistence();
        }
        return persistence;
    }

    public static void resetPersistence() {
        persistence = null;
    }
}
