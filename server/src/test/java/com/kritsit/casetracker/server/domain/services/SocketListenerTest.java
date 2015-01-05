package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.domain.Domain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

public class SocketListenerTest extends TestCase {
    IListeningService listener;

    public SocketListenerTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(SocketListenerTest.class);
    }

    public void setUp() {
        listener = new SocketListener();
    }

    public void testCreation() {
        assertTrue(listener instanceof IListeningService);
    }

    public void testIsListen() {
        assertFalse(listener.isListening());
    }

    public void testListen_IllegalArgumentException() {
        try {
            listener.listen(65540);
        } catch (IllegalArgumentException ex) {
            assertTrue("Port number not in range".equals(ex.getMessage()));
        }
    }

    public void tearDown() {
        try {
            listener.stop();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Domain.resetPersistenceService();
    }
}
