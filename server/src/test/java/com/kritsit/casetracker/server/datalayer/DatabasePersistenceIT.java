package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.server.datalayer.DatabasePersistence;
import com.kritsit.casetracker.server.datalayer.IPersistenceService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    public void testOpen() {
        assertTrue(db.isOpen());
    }

    public void testInsertQuery() throws SQLException {
        String firstName = "test";
        String lastName = "test";
        String username = "test";
        String passwordHash = "-1";
        String insert = "INSERT INTO staff VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);";
        String select = "SELECT * FROM staff WHERE firstName=? AND lastName=? " +
            "AND username=? AND passwordHash=?;";
        db.executeUpdate(insert, firstName, lastName, "test", "test", username, 
                passwordHash, "-1", "-1");
        List<Map<String, String>> result = db.executeQuery(select, firstName, 
                lastName, username, passwordHash);
        assertFalse(result.isEmpty());
    }

    public void tearDown() throws SQLException {
        String firstName = "test";
        String lastName = "test";
        String username = "test";
        String passwordHash = "-1";
        String delete = "DELETE FROM staff WHERE firstName=? AND lastName=? " +
            "AND username=? AND passwordHash=?;";
        db.executeUpdate(delete, firstName, lastName, username, passwordHash);
        db.close();
    }
}
