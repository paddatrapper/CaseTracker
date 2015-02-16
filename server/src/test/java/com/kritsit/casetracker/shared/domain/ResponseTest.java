package com.kritsit.casetracker.shared.domain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ResponseTest extends TestCase {
    Response response;

    public ResponseTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ResponseTest.class);
    }

    public void setUp() {
        response = new Response(200, "This is a message");
    }

    public void testGetStatus() {
        assertTrue(200 == response.getStatus());
    }

    public void testGetBody() {
        assertTrue("This is a message".equals(response.getBody()));
    }
}
