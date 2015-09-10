package com.kritsit.casetracker.server.domain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map;

public class ConfigurationTest extends TestCase {
    public ConfigurationTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ConfigurationTest.class);
    }

    public void testDatabaseProperties() throws IOException {
        Map<String, String> database = readConfiguration();
        assertTrue(database.get("host").equals(Configuration.getDbHost()));
        assertTrue(database.get("port").equals("" + Configuration.getDbPort()));
        assertTrue(database.get("schema").equals(Configuration.getDbSchema()));
        assertTrue(database.get("username").equals(Configuration.getDbUsername()));
        assertTrue(database.get("password").equals(Configuration.getDbPassword()));
    }

    private Map<String, String> readConfiguration() throws IOException {
        Properties config = new Properties();
        InputStream in = new FileInputStream(new File("config.properties"));
        config.load(in);
        in.close();
        Map<String, String> database = new HashMap<>();
        database.put("host", config.getProperty("db-host"));
        database.put("port", config.getProperty("db-port"));
        database.put("schema", config.getProperty("db-schema"));
        database.put("username", config.getProperty("db-user"));
        database.put("password", config.getProperty("db-password"));
        return database;
    }
}
