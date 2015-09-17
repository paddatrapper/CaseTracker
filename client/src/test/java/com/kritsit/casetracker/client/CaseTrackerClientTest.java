package com.kritsit.casetracker.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CaseTrackerClientTest extends TestCase {

    private CaseTrackerClient client;
    
    public CaseTrackerClientTest(String name) {
       super(name);
    }

    public static Test suite() {
        return new TestSuite(CaseTrackerClientTest.class);
    }

    public void setUp() {
        client = new CaseTrackerClient();
    }

    public void testGetVersion() {
        String version = CaseTrackerClient.getVersion();
        assertTrue("0.2.1-ALPHA".equals(version));
    }
}
