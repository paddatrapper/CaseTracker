package com.kritsit.casetracker.server.domain;

import com.kritsit.casetracker.server.domain.services.IPersistenceService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DomainTest extends TestCase {
    public DomainTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(DomainTest.class);
    }

    public void testPersistenceCreation() {
        IPersistenceService persistence = Domain.getPersistenceService();

        assertTrue(persistence instanceof IPersistenceService);
        assertTrue(persistence.equals(Domain.getPersistenceService()));
    }

    public void tearDown() {
        Domain.resetPersistenceService();
    }
}
