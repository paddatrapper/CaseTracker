package com.kritsit.casetracker.client.validation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ArrayValidatorTest extends TestCase {
    IArrayValidator validator;

    public ArrayValidatorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ArrayValidatorTest.class);
    }

    public void setUp() {
        validator = new ArrayValidator<String>();
    }

    public void testContains() {
        String[] array = {"Bob", "Mitchell", "Boet", "Cat", "Dog"};
        assertTrue(validator.contains("Boet", array));
        assertFalse(validator.contains("John", array));
        assertFalse(validator.contains(2, array));
    }

    public void testIsEmpty() {
        String[] array = {"Yankee", "Doodle"};
        String[] emptyArray = {};

        assertFalse(validator.isEmpty(array));
        assertTrue(validator.isEmpty(emptyArray));
    }
}
