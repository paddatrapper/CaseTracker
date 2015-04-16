package com.kritsit.casetracker.client.domain.services;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.client.domain.Domain;
import com.kritsit.casetracker.shared.domain.model.Staff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ServerLoginTest extends TestCase {
    IConnectionService connection;
    ILoginService loginService;

    public ServerLoginTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ServerLoginTest.class);
    }

    public void setUp() {
        connection = mock(IConnectionService.class);
        loginService = new ServerLogin(connection);
    }

    public void testCreation() {
        assertTrue(loginService instanceof ILoginService);
    }

    public void testLoginAttempt_ConnectionClosedOpenFail() {
        String username = "inspector";
        String password = "inspector";
        String host = "localhost";
        int port = 1244;
        when(connection.isOpen()).thenReturn(false);
        when(connection.open(host, port)).thenReturn(false);

        boolean result = loginService.login(username, password);

        assertFalse(result);
        verify(connection).isOpen();
    }

    public void testLoginAttempt_IncorrectUser() {
        String username = "wrongInspector";
        String password = "inspector";
        when(connection.isOpen()).thenReturn(true);
        when(connection.login(username, password.hashCode())).thenReturn(false);

        boolean succeeded = loginService.login(username, password);

        assertFalse(succeeded);
        verify(connection).isOpen();
        verify(connection).login(username, password.hashCode());
    }

    public void testLoginAttempt_IncorrectPassword() {
        String username = "inspector";
        String password = "wrong inspector";
        when(connection.isOpen()).thenReturn(true);
        when(connection.login(username, password.hashCode())).thenReturn(false);

        boolean succeeded = loginService.login(username, password);

        assertFalse(succeeded);
        verify(connection).isOpen();
        verify(connection).login(username, password.hashCode());
    }
    
    public void testLoginAttempt_SucceededOpenConnection() {
        String username = "inspector";
        String password = "inspector";
        String host = "localhost";
        int port = 1244;
        when(connection.isOpen()).thenReturn(false);
        when(connection.open(host, port)).thenReturn(true);
        when(connection.login(username, password.hashCode())).thenReturn(true);

        boolean succeeded = loginService.login(username, password);

        assertTrue(succeeded);
        verify(connection).isOpen();
        verify(connection).login(username, password.hashCode());
    }

    public void testLoginAttempt_Succeeded() {
        String username = "inspector";
        String password = "inspector";
        when(connection.isOpen()).thenReturn(true);
        when(connection.login(username, password.hashCode())).thenReturn(true);

        boolean succeeded = loginService.login(username, password);

        assertTrue(succeeded);
        verify(connection).isOpen();
        verify(connection).login(username, password.hashCode());
    }

    public void testGetUser() {
        String username = "inspector";
        String password = "inspector";
        Staff returnUser = mock(Staff.class);
        when(connection.isOpen()).thenReturn(true);
        when(connection.getUser(username, password.hashCode())).thenReturn(returnUser);

        Staff user = loginService.getUser(username, password);

        assertTrue(user != null);
        verify(connection).isOpen();
        verify(connection).getUser(username, password.hashCode());
    }
}
