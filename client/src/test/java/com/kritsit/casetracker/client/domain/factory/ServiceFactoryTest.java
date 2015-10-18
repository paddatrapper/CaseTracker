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
        assertNotNull(connection);
        assertEquals(connection, ServiceFactory.getServerConnection());
    }

    public void testGetLoginService() {
        assertNotNull(ServiceFactory.getLoginService());
    }

    public void testGetEditorService() {
        Staff user = mock(Staff.class);
        assertNotNull(ServiceFactory.getEditorService(user));
    }
    
    public void testGetAdministratorService() {
        Staff user = mock(Staff.class);
        assertNotNull(ServiceFactory.getAdministratorService(user));
    }
    
    public void testGetMenuService() {
        Staff user = mock(Staff.class);
        assertNotNull(ServiceFactory.getMenuService(user));
    }

    public void tearDown() {
        ServiceFactory.resetServerConnection();
    }
}
