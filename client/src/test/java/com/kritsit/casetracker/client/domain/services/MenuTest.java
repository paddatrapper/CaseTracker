package com.kritsit.casetracker.client.domain.services;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.shared.domain.model.Staff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

public class MenuTest extends TestCase {

    public MenuTest(String name){
        super(name);
    }
    
    public static Test suite(){
        return new TestSuite(MenuTest.class);
    }
    
    public void testChangePassword(){
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IMenuService menuService = new Menu(user, connection);
        when(connection.changePassword("johndoe", 1234, 4321)).thenReturn(true);
        assertTrue(menuService.changePassword("johndoe", 1234, 4321)==200);
        assertTrue(menuService.changePassword("johndoe", 1234, 1234)==500);
    }

    public void testCloseConnection() throws IOException {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IMenuService menuService = new Menu(user, connection);

        menuService.closeConnection();

        verify(connection).close();
    }
}

