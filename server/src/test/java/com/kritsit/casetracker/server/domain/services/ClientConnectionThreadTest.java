package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.domain.Domain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ClientConnectionThreadTest extends TestCase {
    IClientConnectionService connectionThread;
    ServerSocket listener; 

    public ClientConnectionThreadTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ClientConnectionThreadTest.class);
    }

    public void setUp() {
        try {
            listener = new ServerSocket(1244);
            InetAddress localHost = InetAddress.getLocalHost();
            Socket connection = new Socket(localHost, 1244);
            connectionThread = new ClientConnectionThread(listener.accept());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Domain.getPersistenceService().open();
    }

    public void testCreation() {
        assertTrue(connectionThread instanceof IClientConnectionService);
    }

    public void testConnect() {
        connectionThread.setConnectedClient("testClient");
        assertTrue("testClient".equals(connectionThread.getConnectedClient()));
    }

    public void testLogin_Correct() {
        String username = "inspector";
        String password = "inspector";
        Map<String, String> response = connectionThread.login(username, password.hashCode());
        assertTrue("authenticated".equals(response.get("status")));
        assertTrue("inspector".equals(response.get("username")));
        assertTrue("inspector".equals(response.get("firstName")));
        assertTrue("inspector".equals(response.get("lastName")));
        assertTrue("1".equals(response.get("permissions")));
        assertTrue("Inspectorate".equals(response.get("department")));
        assertTrue("Manager".equals(response.get("position")));
    }

    public void testLogin_IncorrectUsername() {
        String username = "incorrectUser";
        String password = "testPassword";
        Map<String, String> response = connectionThread.login(username, password.hashCode());
        assertTrue("authentication failed".equals(response.get("status")));
        assertTrue("incorrectUser".equals(response.get("username")));
    }

    public void tearDown() {
        try {
            connectionThread.close();
            listener.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            System.err.println("Connection thread or listener is null");
        }
        Domain.getPersistenceService().close();
        Domain.resetPersistenceService();
    }
}
