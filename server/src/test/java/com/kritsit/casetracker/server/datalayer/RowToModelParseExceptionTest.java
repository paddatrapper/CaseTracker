package com.kritsit.casetracker.server.datalayer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

public class RowToModelParseExceptionTest extends TestCase {

    public RowToModelParseExceptionTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(RowToModelParseExceptionTest.class);
    }

    public void testGetMessage() {
        IOException e = new IOException("test cause");
        RowToModelParseException ex = new RowToModelParseException("This is a test", e);
        assertTrue("This is a test".equals(ex.getMessage()));
    }
}
