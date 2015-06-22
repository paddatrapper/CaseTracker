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

    public void testCreation() {
        assertTrue(db instanceof IPersistenceService);
    }

    public void testOpen() {
        assertTrue(db.isOpen());
    }

    public void testInsertQuery() throws SQLException {
        String insert = "INSERT INTO staff VALUES (NULL, 'test', 'test', 'test', 'test', 'test', -1, -1, -1);";
        String select = "SELECT * FROM staff WHERE firstName='test' AND lastName='test' AND username='test' AND passwordHash=-1;";
        db.executeUpdate(insert);
        List<Map<String, String>> result = db.executeQuery(select);
        assertFalse(result.isEmpty());
    }

    public void tearDown() throws SQLException {
        String delete = "DELETE FROM staff WHERE firstName='test' AND lastName='test' AND username='test' AND passwordHash=-1;";
        db.executeUpdate(delete);
        db.close();
    }
}
