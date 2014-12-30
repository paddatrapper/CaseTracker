package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.Domain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LoginServiceTest extends TestCase {
    ILoginService loginService;

    public LoginServiceTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(LoginServiceTest.class);
    }

    public void setUp() {
        char[] password = {'m', 'y', ' ', 'p', 'a', 's'};
        IConnectionService connection = Domain.getServerConnection();
        loginService = new ServerLogin(connection);
    }

    public void testCreation() {
        assertTrue(loginService instanceof ILoginService);
    }
}
