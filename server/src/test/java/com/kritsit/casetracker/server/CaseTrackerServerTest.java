package com.kritsit.casetracker.server;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CaseTrackerServerTest extends TestCase {
    private CaseTrackerServer server;

    public CaseTrackerServerTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(CaseTrackerServerTest.class);
    }

    public void setUp() {
        server = new CaseTrackerServer(1244);
    }

    public void testCreation() {
        assertTrue(server instanceof CaseTrackerServer);
    }

    public void testVersion() {
        String version = CaseTrackerServer.getVersion();
        assertTrue("0.1a".equals(version));
    }
}
