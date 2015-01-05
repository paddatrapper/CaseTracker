package com.kritsit.casetracker.server.domain.services;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DatabasePersistenceTest extends TestCase {
    private IPersistenceService db;

    public DatabasePersistenceTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(DatabasePersistenceTest.class);
    }

    public void setUp() {
        db = new DatabasePersistence();
    }

    public void testCreation() {
        assertTrue(db instanceof IPersistenceService);
    }

    public void testOpen() {
        assertFalse(db.isOpen());
        db.open();
        assertTrue(db.isOpen());
    }

    public void tearDown() {
        db.close();
    }
}
