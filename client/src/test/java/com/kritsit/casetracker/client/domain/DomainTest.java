package com.kritsit.casetracker.client.domain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DomainTest extends TestCase {

    public DomainTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(DomainTest.class);
    }

    public void testGetServerAddress() {
        assertTrue("localhost".equals(Domain.getServerAddress()));
    }

    public void testGetConnectionPort() {
        assertTrue(Domain.getServerConnectionPort() == 1244);
    }
}
