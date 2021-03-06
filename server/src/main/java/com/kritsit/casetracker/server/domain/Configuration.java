package com.kritsit.casetracker.server.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map;

public class Configuration {
    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    private static Map<String, String> database;
    private static String clientVersion;
    
    public static String getDbHost() {
        return getDatabaseProperty("host");
    }

    public static int getDbPort() {
        return Integer.parseInt(getDatabaseProperty("port"));
    }

    public static String getDbSchema() {
        return getDatabaseProperty("schema");
    }

    public static String getDbUsername() {
        return getDatabaseProperty("username");
    }

    public static String getDbPassword() {
        return getDatabaseProperty("password");
    }

    public static String getClientVersion() {
        if (clientVersion == null) {
            readConfiguration();
        }
        return clientVersion;
    }

    private static String getDatabaseProperty(String key) {
        logger.info("Retriving property {}", key);
        if (database == null) {
            readConfiguration();
        }
        return database.get(key);
    }

    private static void readConfiguration() {
        logger.info("Reading configuration file");
        Properties config = new Properties();
        try {
            InputStream in = new FileInputStream(new File("config.properties"));
            config.load(in);
            in.close();
            database = new HashMap<String, String>();
            database.put("host", config.getProperty("db-host"));
            database.put("port", config.getProperty("db-port"));
            database.put("schema", config.getProperty("db-schema"));
            database.put("username", config.getProperty("db-user"));
            database.put("password", config.getProperty("db-password"));
            clientVersion = config.getProperty("client-version");
        } catch (NullPointerException | IOException ex) {
            logger.error("Unable to read configuration", ex);
        }
    }
}
