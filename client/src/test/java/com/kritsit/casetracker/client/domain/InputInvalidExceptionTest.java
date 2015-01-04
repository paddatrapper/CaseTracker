package com.kritsit.casetracker.client.domain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class InputInvalidExceptionTest extends TestCase {
    private InputInvalidException exception;

    public InputInvalidExceptionTest(String name) {
         super(name);
    }

    public static Test suite() {
        return new TestSuite(InputInvalidExceptionTest.class);
    }

    public void setUp() {
        exception = new InputInvalidException("Test exception");
    }

    public void testLocalizedMessage() {
        assertTrue("Input was invalid - Test exception".equals(exception.getLocalizedMessage()));
    }
}
