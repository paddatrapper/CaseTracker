package com.kritsit.casetracker.server.domain.services;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

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
    }

    public void testCreation() {
        assertTrue(connectionThread instanceof IClientConnectionService);
    }

    public void testConnect() {
        connectionThread.setConnectedClient("testClient");
        assertTrue("testClient".equals(connectionThread.getConnectedClient()));
    }

    public void tearDown() {
        try {
            connectionThread.close();
            listener.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
