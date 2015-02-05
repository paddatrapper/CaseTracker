package com.kritsit.casetracker.server.domain.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AuthenticationExceptionTest extends TestCase {
    AuthenticationException authenticationException;

    public AuthenticationExceptionTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(AuthenticationExceptionTest.class);
    }

    public void setUp() {
        authenticationException = new AuthenticationException();
    }

    public void testCreation() {
        assertTrue(authenticationException.getClass() == AuthenticationException.class);
    }
}
