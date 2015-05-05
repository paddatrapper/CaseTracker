package com.kritsit.casetracker.client.domain.factory;

import com.kritsit.casetracker.client.domain.services.IConnectionService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ServiceFactoryTest extends TestCase {
    IConnectionService connection;

    public ServiceFactoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ServiceFactoryTest.class);
    }

    public void setUp() {
    }

    public void testGetServerConnection() {
        connection = ServiceFactory.getServerConnection();
        assertTrue(connection != null);
        assertTrue(connection.equals(ServiceFactory.getServerConnection()));
    }

    public void testGetLoginService() {
        assertTrue(ServiceFactory.getLoginService() != null);
    }

    public void tearDown() {
        ServiceFactory.resetServerConnection();
    }
}
