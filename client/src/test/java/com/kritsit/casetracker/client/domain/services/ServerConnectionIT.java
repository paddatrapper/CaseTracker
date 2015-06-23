package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.factory.ServiceFactory;
import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Staff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.List;

public class ServerConnectionIT extends TestCase {
    String host = "localhost";
    int port = 1244;
    IConnectionService connection;

    public ServerConnectionIT(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ServerConnectionIT.class);
    }

    public void setUp() {
        connection = ServiceFactory.getServerConnection();
    }

    public void testConnection_PortOutOfBounds() {
        try {
            connection.open(host, 65555);
        } catch (IllegalArgumentException ex) {
            assertTrue("Port must be in range".equals(ex.getMessage()));
        }
    }

    public void testConnection_UnknownHostException() {
        try {
            connection.open("ThisIsNotAValidHost", port);
        } catch (IllegalArgumentException ex) {
            assertTrue("Host not found".equals(ex.getMessage()));
        }
    }

    public void testConnection_Succeed() {
        assertTrue(connection.open(host, port));
    }

    public void testLogin_Correct() {
        connection.open(host, port);
        assertTrue(connection.login("inspector", "inspector".hashCode()));
    }

    public void testLogin_Incorrect() {
        connection.open(host, port);
        assertFalse(connection.login("test", "".hashCode()));
    }
    
    public void testGetUser() {
        connection.open(host, port);
        assertTrue(connection.getUser("inspector", "inspector".hashCode()) != null);
    }

    public void testGetCases_NoUser() {
        connection.open(host, port);
        List<Case> caseList = connection.getCases(null);
        assertTrue(caseList != null);
    }

    public void testGetCases_User() {
        connection.open(host, port);
        Staff user = new Staff("inspector", "test", "inspector", "department", "position", Permission.EDITOR);
        List<Case> caseList = connection.getCases(user);
        assertTrue(caseList != null);
    }

    public void testGetInspectors() {
        connection.open(host, port);
        assertTrue(connection.getInspectors() != null);
    }
    
    public void testGetLastCaseNumber() {
        connection.open(host, port);
        assertFalse("0000-00-0000".equals(connection.getLastCaseNumber()));
    }

    public void testAddCase() {
        connection.open(host, port);
        Case c = new Case();
        assertTrue(connection.addCase(c));
    }

    public void tearDown() throws IOException {
        ServiceFactory.resetServerConnection();
        if (connection.isOpen()) {
            connection.close();
        }
    }
}
