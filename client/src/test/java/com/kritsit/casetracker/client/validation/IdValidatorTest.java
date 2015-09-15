package com.kritsit.casetracker.client.validation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IdValidatorTest extends TestCase {
    IValidator<String> validator;

    public IdValidatorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(IdValidatorTest.class);
    }

    public void setUp() {
        validator = new IdValidator();
    }

    public void test_ValidateNull() {
        assertFalse(validator.validate(null));
    }

    public void test_ValidateDifferentClass() {
        assertFalse(validator.validate(1));
    }

    public void test_ValidateEmpty() {
        assertFalse(validator.validate(""));
    }

    public void test_ValidateShort() {
        assertFalse(validator.validate("960221509"));
    }

    public void test_ValidateFailure() {
        assertFalse(validator.validate("9602215094083"));
    }

    public void test_ValidateSuccess() {
        assertTrue(validator.validate("9602215094081"));
    }
}
