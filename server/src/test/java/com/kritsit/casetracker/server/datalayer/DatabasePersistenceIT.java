package com.kritsit.casetracker.server.datalayer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.kritsit.casetracker.server.datalayer.DatabasePersistence;
import com.kritsit.casetracker.server.datalayer.IPersistenceService;

public class DatabasePersistenceIT extends TestCase {
    private IPersistenceService db;

    public DatabasePersistenceIT(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(DatabasePersistenceIT.class);
    }

    public void setUp() {
        db = new DatabasePersistence();
        db.open();
    }

    public void testCreation() {
        assertNotNull(db);
    }

    public void testOpen() {
        assertTrue(db.isOpen());
    }
    
    public void tearDown() {
        db.close();
    }
}
