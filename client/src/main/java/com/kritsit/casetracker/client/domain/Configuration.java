package com.kritsit.casetracker.client.domain;

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
    private static Map<String, String> configuration;

    public static String getServer() {
        return getProperty("server");
    }

    public static int getPort() {
        return Integer.parseInt(getProperty("port"));
    }
    private static String getProperty(String key) {
        logger.info("Retriving property {}", key);
        if (configuration == null) {
            readConfiguration();
        }
        return configuration.get(key);
    }

    private static void readConfiguration() {
        logger.info("Reading configuration file");
        Properties config = new Properties();
        try {
            InputStream in = new FileInputStream(new File("config.properties"));
            config.load(in);
            in.close();
            configuration = new HashMap<String, String>();
            configuration.put("server", config.getProperty("server"));
            configuration.put("port", config.getProperty("port"));
        } catch (NullPointerException | IOException ex) {
            logger.error("Unable to read configuration", ex);
        }
    }
}
