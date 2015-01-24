package com.kritsit.casetracker.client.domain.datastructures;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DefendantTest extends TestCase {
    Defendant defendant;

    public DefendantTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(DefendantTest.class);
    }

    public void setUp() {
        defendant = new Defendant("0002225094081", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com", false);
    }

    public void testAccessors() {
        assertFalse(defendant.isSecondOffence());
    }

    public void testMutators() {
        defendant.setSecondOffence(true);
        assertTrue(defendant.isSecondOffence());
    }

    public void testToString() {
        assertTrue("Defendant: John Smith (0002225094081)".equals(defendant.toString()));
    }
}
