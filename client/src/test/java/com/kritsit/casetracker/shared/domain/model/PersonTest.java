package com.kritsit.casetracker.shared.domain.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PersonTest extends TestCase {
    Person person;

    public PersonTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(PersonTest.class);
    }

    public void setUp() {
        person = new Person("0002225094081", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com");
    }

    public void testAccessors() {
        assertTrue("0002225094081".equals(person.getId()));
        assertTrue("John".equals(person.getFirstName()));
        assertTrue("Smith".equals(person.getLastName()));
        assertTrue("20 Church Road, Cape Town".equals(person.getAddress()));
        assertTrue("0211234567".equals(person.getTelephoneNumber()));
        assertTrue("email@address.com".equals(person.getEmailAddress()));
        assertTrue("John Smith".equals(person.getName()));
    }

    public void testMutators() {
        person.setId("1234567890123");
        person.setFirstName("Bob");
        person.setLastName("van der Merwe");
        person.setAddress("200 Roach Road, Cape Town");
        person.setTelephoneNumber("123456789");
        person.setEmailAddress("second@email.net");

        assertTrue("1234567890123".equals(person.getId()));
        assertTrue("Bob".equals(person.getFirstName()));
        assertTrue("van der Merwe".equals(person.getLastName()));
        assertTrue("200 Roach Road, Cape Town".equals(person.getAddress()));
        assertTrue("123456789".equals(person.getTelephoneNumber()));
        assertTrue("second@email.net".equals(person.getEmailAddress()));
    }

    public void testToString() {
        assertTrue("Person: John Smith (0002225094081)".equals(person.toString()));
    }

    public void testEquals_Null() {
        assertFalse(person.equals(null));
    }

    public void testEquals_Class() {
        assertFalse(person.equals("test"));
    }

    public void testEquals() {
        Person d = new Person("0002225094081", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com");

        assertTrue(person.equals(d));
    }
}
