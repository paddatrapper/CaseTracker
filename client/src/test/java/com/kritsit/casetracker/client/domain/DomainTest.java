package com.kritsit.casetracker.client.domain;

import com.kritsit.casetracker.client.domain.services.IConnectionService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DomainTest extends TestCase {
    IConnectionService connection;

    public DomainTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(DomainTest.class);
    }

    public void setUp() {
        connection = Domain.getServerConnection();
    }

    public void testCreation() {
        assertTrue(connection instanceof IConnectionService);
    }

    public void testSingleton() {
        assertTrue(connection.equals(Domain.getServerConnection()));
    }
    
    public void tearDown() {
        Domain.resetServerConnection();
    }
}