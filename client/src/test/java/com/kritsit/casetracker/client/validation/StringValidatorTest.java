package com.kritsit.casetracker.client.validation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StringValidatorTest extends TestCase {
    IValidator<String> validator;

    public StringValidatorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(StringValidatorTest.class);
    }

    public void setUp() {
        validator = new StringValidator();
    }

    public void test_ValidateNull() {
        assertFalse(validator.validate(null));
    }

    public void test_ValidateEmpty() {
        assertFalse(validator.validate(""));
    }

    public void test_ValidateSuccess() {
        assertTrue(validator.validate("string"));
    }
}
