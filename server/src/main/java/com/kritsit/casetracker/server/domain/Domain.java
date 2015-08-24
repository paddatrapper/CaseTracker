package com.kritsit.casetracker.server.domain;

import com.kritsit.casetracker.server.datalayer.DatabasePersistence;
import com.kritsit.casetracker.server.datalayer.IPersistenceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Domain {
    private static final Logger logger = LoggerFactory.getLogger(Domain.class);
    private static IPersistenceService persistence;

    public static synchronized IPersistenceService getPersistenceService() {
        if (persistence == null) {
            logger.info("Creating new persistence connection");
            persistence = new DatabasePersistence();
        }
        return persistence;
    }

    public static void resetPersistenceService() {
        logger.debug("Resetting persistence object");
        persistence = null;
    }
}
