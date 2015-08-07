package com.kritsit.casetracker.client.domain.factory;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.client.domain.services.IConnectionService;
import com.kritsit.casetracker.shared.domain.model.Staff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ServiceFactoryTest extends TestCase {
    public ServiceFactoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ServiceFactoryTest.class);
    }

    public void setUp() {}

    public void testGetServerConnection() {
        IConnectionService connection = ServiceFactory.getServerConnection();
        assertTrue(connection != null);
        assertTrue(connection.equals(ServiceFactory.getServerConnection()));
    }

    public void testGetLoginService() {
        assertTrue(ServiceFactory.getLoginService() != null);
    }

    public void testGetEditorService() {
        Staff user = mock(Staff.class);
        assertTrue(ServiceFactory.getEditorService(user) != null);
    }
    
    public void testGetAdministratorService() {
        Staff user = mock(Staff.class);
        assertTrue(ServiceFactory.getAdministratorService(user) != null);
    }
    
    public void testGetMenuService() {
        Staff user = mock(Staff.class);
        assertTrue(ServiceFactory.getMenuService(user) != null);
    }

    public void tearDown() {
        ServiceFactory.resetServerConnection();
    }
}
