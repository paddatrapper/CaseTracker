package com.kritsit.casetracker.server.datalayer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RepositoryFactoryTest extends TestCase {

    public RepositoryFactoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(RepositoryFactoryTest.class);
    }

    public void testGetCaseRepository() {
        assertNotNull(RepositoryFactory.getCaseRepository());
    }

    public void testGetEvidenceRepository() {
        assertNotNull(RepositoryFactory.getEvidenceRepository());
    }

    public void testGetIncidentRepository() {
        assertNotNull(RepositoryFactory.getIncidentRepository());
    }

    public void testGetPersonRepository() {
        assertNotNull(RepositoryFactory.getPersonRepository());
    }

    public void testGetUserRepository() {
        assertNotNull(RepositoryFactory.getUserRepository());
    }

    public void testGetVehicleRepository() {
        assertNotNull(RepositoryFactory.getVehicleRepository());
    }
}
