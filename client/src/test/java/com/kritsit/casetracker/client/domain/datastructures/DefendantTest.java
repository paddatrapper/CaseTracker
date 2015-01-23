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
        assertTrue("0002225094081".equals(defendant.getId()));
        assertTrue("John".equals(defendant.getFirstName()));
        assertTrue("Smith".equals(defendant.getLastName()));
        assertTrue("20 Church Road, Cape Town".equals(defendant.getAddress()));
        assertTrue("0211234567".equals(defendant.getTelephoneNumber()));
        assertTrue("email@address.com".equals(defendant.getEmailAddress()));
        assertFalse(defendant.isSecondOffence());
        assertTrue("John Smith".equals(defendant.getName()));
    }
}
