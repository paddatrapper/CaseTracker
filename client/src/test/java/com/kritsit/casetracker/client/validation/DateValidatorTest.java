package com.kritsit.casetracker.client.validation;

import com.kritsit.casetracker.client.domain.model.Period;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.time.LocalDate;

public class DateValidatorTest extends TestCase {

    public DateValidatorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(DateValidatorTest.class);
    }

    public void test_ValidateNull() {
        IValidator<LocalDate> validator = new DateValidator(Period.BEFORE, LocalDate.now());
        assertFalse(validator.validate(null));
    }

    public void test_ValidateDifferentClass() {
        IValidator<LocalDate> validator = new DateValidator(Period.BEFORE, LocalDate.now());
        assertFalse(validator.validate(1));
    }

    public void test_ValidateBefore() {
        IValidator<LocalDate> validator = new DateValidator(Period.BEFORE, LocalDate.now());
        assertFalse(validator.validate(LocalDate.now()));
        assertTrue(validator.validate(LocalDate.parse("1950-01-01")));
    }

    public void test_ValidateEquals() {
        IValidator<LocalDate> validator = new DateValidator(Period.EQUALS, LocalDate.now());
        assertTrue(validator.validate(LocalDate.now()));
        assertFalse(validator.validate(LocalDate.parse("1950-01-01")));
    }

    public void test_ValidateAfter() {
        IValidator<LocalDate> validator = new DateValidator(Period.AFTER, LocalDate.now());
        assertTrue(validator.validate(LocalDate.now().plusDays(1)));
        assertFalse(validator.validate(LocalDate.parse("1950-01-01")));
    }

    public void test_ValidateNone() {
        IValidator<LocalDate> validator = new DateValidator();
        assertTrue(validator.validate(LocalDate.now()));
    }
}
