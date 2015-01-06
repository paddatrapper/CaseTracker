package com.kritsit.casetracker.server.domain.services;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Map;

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
        db.open();
    }

    public void testCreation() {
        assertTrue(db instanceof IPersistenceService);
    }

    public void testOpen() {
        assertTrue(db.isOpen());
    }

    public void testGetPasswordSaltedHash() {
        String username = "inspector";
        long expectedSalt = -5922475058261058398L;
        long salt = db.getSalt(username);
        assertTrue(expectedSalt == salt);

        long expectedPasswordSaltedHash = "inspector".hashCode() + expectedSalt;
        long passwordSaltedHash = db.getPasswordSaltedHash(username);
        assertTrue(expectedPasswordSaltedHash == passwordSaltedHash);
    }

    public void testGetUserDetails() {
        String username = "inspector";
        Map<String, String> response = db.getUserDetails(username);
        assertFalse(response == null);
        assertTrue("inspector".equals(response.get("firstName")));
        assertTrue("inspector".equals(response.get("lastName")));
        assertTrue("1".equals(response.get("permissions")));
        assertTrue("Inspectorate".equals(response.get("department")));
        assertTrue("Manager".equals(response.get("position")));
    }

    public void tearDown() {
        db.close();
    }
}
