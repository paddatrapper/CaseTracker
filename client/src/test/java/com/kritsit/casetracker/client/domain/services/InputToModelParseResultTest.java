package com.kritsit.casetracker.client.domain.services;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class InputToModelParseResultTest extends TestCase {

    public InputToModelParseResultTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(InputToModelParseResultTest.class);
    }

    public void testCreation() {
        InputToModelParseResult blank = new InputToModelParseResult(false);
        InputToModelParseResult message = new InputToModelParseResult(false, "Test message");
        assertTrue("Input failed to parse into model".equals(blank.getReason()));
        assertTrue("Test message".equals(message.getReason()));
    }

    public void testGetMessage_ConstructorParameter() {
        InputToModelParseResult result = new InputToModelParseResult(false, "This is a test");
        assertTrue("This is a test".equals(result.getReason()));
    }

    public void testAddFailedInput() {
        InputToModelParseResult result = new InputToModelParseResult(true);
        result.addFailedInput("Case name");
        assertFalse(result.isSuccessful());
    }

    public void testGetMessage_Successful() {
        InputToModelParseResult result = new InputToModelParseResult(true);
        assertTrue("Case created successfully".equals(result.getReason()));
    }

    public void testGetMessage_OneFailure() {
        InputToModelParseResult result = new InputToModelParseResult(false);
        result.addFailedInput("Case name");
        assertTrue("Case name required".equals(result.getReason()));
    }

    public void testGetMessage_TwoFailures() {
        InputToModelParseResult result = new InputToModelParseResult(false);
        result.addFailedInput("Case name");
        result.addFailedInput("Case type");
        assertTrue("Case name and Case type required".equals(result.getReason()));
    }

    public void testGetMessage_MultipleFailures() {
        InputToModelParseResult result = new InputToModelParseResult(false);
        result.addFailedInput("Case name");
        result.addFailedInput("Case type");
        result.addFailedInput("Incident date");
        assertTrue("Case name, Case type and Incident date required".equals(result.getReason()));
    }
}
