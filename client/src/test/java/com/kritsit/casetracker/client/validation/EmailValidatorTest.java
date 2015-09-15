package com.kritsit.casetracker.client.validation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class EmailValidatorTest extends TestCase {
    IValidator<String> validator;

    public EmailValidatorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(EmailValidatorTest.class);
    }

    public void setUp() {
        validator = new EmailValidator();
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

    public void test_ValidateAtStart() {
        assertFalse(validator.validate("@testing.com"));
    }

    public void test_ValidateNoTld() {
        assertFalse(validator.validate("test@testing."));
    }

    public void test_ValidateNoAt() {
        assertFalse(validator.validate("testtesting"));
    }

    public void test_ValidateSuccess() {
        assertTrue(validator.validate("test@testing.com"));
    }
}
