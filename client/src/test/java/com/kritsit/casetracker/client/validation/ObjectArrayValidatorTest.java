package com.kritsit.casetracker.client.validation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ObjectArrayValidatorTest extends TestCase {
    IArrayValidator validator;

    public ObjectArrayValidatorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ObjectArrayValidatorTest.class);
    }

    public void setUp() {
    }

    public void testContains() {
        validator = new ObjectArrayValidator<String>();
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

    public void testIsNull() {
        String[] array = {"Bob", "Mitchell"};
        String[] nullArray = null;

        assertFalse(validator.isNull(array));
        assertTrue(validator.isNull(nullArray));
    }
}
