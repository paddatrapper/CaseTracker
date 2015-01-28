package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.domain.Domain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class LoginTest extends TestCase {
    IPersistenceService persistence;
    ILoginService login;

    public LoginTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(LoginTest.class);
    }

    public void setUp() {
        persistence = Domain.getPersistenceService();
        persistence.open();
        login = new Login(persistence);
    }

    public void testCreation() {
        assertTrue(login instanceof ILoginService);
    }

    public void testLoginAttempt_IncorrectUser() {
        int password = "inspector".hashCode();
        String username = "wrongInspector";
        boolean succeeded = login.login(username, password);
        assertFalse(succeeded);
    }

    public void testLoginAttempt_IncorrectPassword() {
        int password = "wrong inspector".hashCode();
        String username = "inspector";
        boolean succeeded = login.login(username, password);
        assertFalse(succeeded);
    }

    public void testLoginAttempt_Succeeded() {
        int password = "inspector".hashCode();
        String username = "inspector";
        boolean succeeded = login.login(username, password);
        assertTrue(succeeded);
    }

    public void tearDown() {
        persistence.close();
        Domain.resetPersistenceService();
    }
}
