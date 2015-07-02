package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.server.domain.Domain;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class RepositoryFactory {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryFactory.class);

    public static ICaseRepository getCaseRepository() {
        logger.info("Creating case repository");
        IPersistenceService persistence = Domain.getPersistenceService();
        IIncidentRepository incidentRepo = getIncidentRepository();
        IPersonRepository personRepo = getPersonRepository();
        IUserRepository userRepo = getUserRepository();
        IEvidenceRepository evidenceRepo = getEvidenceRepository();
        return new CaseRepository(persistence, incidentRepo, personRepo, userRepo, 
                evidenceRepo);
    }

    public static IEvidenceRepository getEvidenceRepository() {
        logger.info("Creating evidence repository");
        IPersistenceService persistence = Domain.getPersistenceService();
        return new EvidenceRepository(persistence);
    }

    public static IIncidentRepository getIncidentRepository() {
        logger.info("Creating incident repository");
        IPersistenceService persistence = Domain.getPersistenceService();
        return new IncidentRepository(persistence);
    }

    public static IPersonRepository getPersonRepository() {
        logger.info("Creating person repository");
        IPersistenceService persistence = Domain.getPersistenceService();
        return new PersonRepository(persistence);
    }

    public static IUserRepository getUserRepository() {
        logger.info("Creating user repository");
        IPersistenceService persistence = Domain.getPersistenceService();
        return new UserRepository(persistence);
    }

    public static IVehicleRepository getVehicleRepository() {
        logger.info("Creating vehicle repository");
        IPersistenceService persistence = Domain.getPersistenceService();
        return new VehicleRepository(persistence);
    }
}
