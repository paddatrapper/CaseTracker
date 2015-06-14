package com.kritsit.casetracker.client.validation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValidatorTest extends TestCase {

    public ValidatorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ValidatorTest.class);
    }

    public void test_ValidateNull() {
        IValidator<String> validator = new Validator<String>(String.class);
        assertFalse(validator.validate(null));
    }

    public void test_ValidateDifferentClass() {
        IValidator<String> validator = new Validator<String>(String.class);
        assertFalse(validator.validate(1));
    }

    public void test_ValidateSuccess() {
        IValidator<String> validator = new Validator<String>(String.class);
        assertTrue(validator.validate("This is not null"));
    }
}
