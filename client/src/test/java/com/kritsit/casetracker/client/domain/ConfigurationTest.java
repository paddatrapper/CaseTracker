package com.kritsit.casetracker.client.domain;

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

    public void testHostSetting() throws IOException {
        Map<String, String> config = readConfiguration();
        assertTrue(config.get("server").equals(Configuration.getServer()));
    }

    public void testPortSetting() throws IOException {
        Map<String, String> config = readConfiguration();
        assertTrue(config.get("port").equals("" + Configuration.getPort()));
    }

    private Map<String, String> readConfiguration() throws IOException {
        Properties config = new Properties();
        InputStream in = new FileInputStream(new File("config.properties"));
        config.load(in);
        in.close();
        Map<String, String> configuration = new HashMap<>();
        configuration.put("server", config.getProperty("server"));
        configuration.put("port", config.getProperty("port"));
        return configuration;
    }
}
