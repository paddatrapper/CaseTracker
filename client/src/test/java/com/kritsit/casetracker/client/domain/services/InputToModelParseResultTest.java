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
        InputToModelParseResult<Boolean> result = new InputToModelParseResult(false, false);
        InputToModelParseResult<Boolean> complete = new InputToModelParseResult(false, "Test message", true);

        assertEquals("Input failed to parse into model", blank.getReason());
        assertEquals("Test message", message.getReason());
        assertFalse(result.getResult());
        assertTrue(complete.getResult());
        assertEquals("Test message", complete.getReason());
    }

    public void testGetMessage_ConstructorParameter() {
        InputToModelParseResult result = new InputToModelParseResult(false, "This is a test");
        assertEquals("This is a test", result.getReason());
    }

    public void testAddFailedInput() {
        InputToModelParseResult result = new InputToModelParseResult(true);
        result.addFailedInput("Case name");
        assertFalse(result.isSuccessful());
    }

    public void testContains() {
        InputToModelParseResult result = new InputToModelParseResult(true);
        result.addFailedInput("Case name");
        assertTrue(result.contains("Case name"));
    }

    public void testGetMessage_Successful() {
        InputToModelParseResult result = new InputToModelParseResult(true, "Case created successfully");
        assertEquals("Case created successfully", result.getReason());
    }

    public void testGetMessage_OneFailure() {
        InputToModelParseResult result = new InputToModelParseResult(false);
        result.addFailedInput("Case name");
        assertEquals("Case name required", result.getReason());
    }

    public void testGetMessage_TwoFailures() {
        InputToModelParseResult result = new InputToModelParseResult(false);
        result.addFailedInput("Case name");
        result.addFailedInput("Case type");
        assertEquals("Case name and Case type required", result.getReason());
    }

    public void testGetMessage_MultipleFailures() {
        InputToModelParseResult result = new InputToModelParseResult(false);
        result.addFailedInput("Case name");
        result.addFailedInput("Case type");
        result.addFailedInput("Incident date");
        assertEquals("Case name, Case type and Incident date required", result.getReason());
    }

    public void testGetResult() {
        InputToModelParseResult<Integer> result = new InputToModelParseResult<>(true, 1);
        assertEquals((Object) 1, result.getResult());
    }

    public void testSetResult() {
        InputToModelParseResult<String> result = new InputToModelParseResult<>(true);
        result.setResult("This String");
        assertEquals("This String", result.getResult());
    }
}
