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
import static org.mockito.Mockito.*;

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
            Socket connection = mock(Socket.class);
            connectionThread = new ClientConnectionThread(listener.accept());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //Domain.getPersistenceService().open();
        connectionThread.setConnectedClient("testClient");
    }

    public void testCreation() {
        assertTrue(connectionThread instanceof IClientConnectionService);
    }

    public void testGetConnectedClient() {
        assertTrue("testClient".equals(connectionThread.getConnectedClient()));
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
        //Domain.getPersistenceService().close();
        //Domain.resetPersistenceService();
    }
}
