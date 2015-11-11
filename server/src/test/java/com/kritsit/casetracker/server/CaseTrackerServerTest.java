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
        server = new CaseTrackerServer();
    }

    public void testVersion() {
        String version = CaseTrackerServer.getVersion();
        assertTrue("1.0.0-BETA".equals(version));
    }

    public void tearDown() {
        server.close();
    }
}
