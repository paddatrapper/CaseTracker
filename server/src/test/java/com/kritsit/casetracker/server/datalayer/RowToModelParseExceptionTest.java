package com.kritsit.casetracker.server.datalayer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RowToModelParseExceptionTest extends TestCase {

    public RowToModelParseExceptionTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(RowToModelParseExceptionTest.class);
    }

    public void testGetMessage() {
        RowToModelParseException ex = new RowToModelParseException("This is a test");
        assertTrue("This is a test".equals(ex.getMessage()));
    }
}
