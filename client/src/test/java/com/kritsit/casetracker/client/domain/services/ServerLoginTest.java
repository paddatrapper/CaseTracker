package com.kritsit.casetracker.client.domain.services;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.client.domain.Domain;

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

    public void testLoginAttempt_ConnectionClosed() {
        String username = "inspector";
        String password = "inspector";
        when(connection.isOpen()).thenReturn(false);

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
}
