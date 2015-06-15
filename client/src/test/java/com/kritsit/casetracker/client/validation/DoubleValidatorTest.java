package com.kritsit.casetracker.client.validation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DoubleValidatorTest extends TestCase {

    public DoubleValidatorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(DoubleValidatorTest.class);
    }

    public void test_ValidateNull() {
        IValidator<Double> validator = new DoubleValidator();
        assertFalse(validator.validate(null));
    }

    public void test_ValidateDifferentClass() {
        IValidator<Double> validator = new DoubleValidator();
        assertFalse(validator.validate("test"));
    }

    public void test_ValidateSuccess() {
        IValidator<Double> validator = new DoubleValidator();
        assertTrue(validator.validate(0.001));
    }
}
