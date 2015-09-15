package com.kritsit.casetracker.client.validation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TelephoneValidatorTest extends TestCase {
    IValidator<String> validator;

    public TelephoneValidatorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(TelephoneValidatorTest.class);
    }

    public void setUp() {
        validator = new TelephoneValidator();
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
        assertFalse(validator.validate("02234567"));
    }

    public void test_ValidateNoLeading0() {
        assertFalse(validator.validate("2234567234"));
    }

    public void test_ValidateSuccess() {
        assertTrue(validator.validate("0211234567"));
    }
}
