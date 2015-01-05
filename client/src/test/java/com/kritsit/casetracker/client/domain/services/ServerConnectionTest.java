package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.Domain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

public class ServerConnectionTest extends TestCase {
    IConnectionService connection;

    public ServerConnectionTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ServerConnectionTest.class);
    }

    public void setUp() {
        connection = Domain.getServerConnection();
    }

    public void tearDown() {
        try {
            connection.close(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {}
        Domain.resetServerConnection();
    }

    public void testConnection_Succeed() {
        assertFalse(connection.open("localhost", 1244)); //TODO: Connection to server.
    }

    public void testConnection_PortOutOfBounds() {
        try {
            connection.open("localhost", 65555);
        } catch (IllegalArgumentException ex) {
            assertTrue("Port must be in range".equals(ex.getMessage()));
        }
    }

    public void testConnection_IOException() {
        assertFalse(connection.open("ThisIsNotAValidHost", 1244));
    }
}
