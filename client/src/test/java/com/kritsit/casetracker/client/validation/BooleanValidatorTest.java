package com.kritsit.casetracker.client.validation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BooleanValidatorTest extends TestCase {

    public BooleanValidatorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(BooleanValidatorTest.class);
    }

    public void test_ValidateNull() {
        IValidator<Boolean> validator = new BooleanValidator();
        assertFalse(validator.validate(null));
    }

    public void test_ValidateDifferentClass() {
        IValidator<Boolean> validator = new BooleanValidator();
        assertFalse(validator.validate(1));
    }

    public void test_ValidateSuccess() {
        IValidator<Boolean> validator = new BooleanValidator();
        assertTrue(validator.validate(false));
    }
}
