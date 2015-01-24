package com.kritsit.casetracker.client.domain.datastructures;

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
        assertTrue("1234567890123".equals(person.getId()));
        person.setFirstName("Bob");
        assertTrue("Bob".equals(person.getFirstName()));
        person.setLastName("van der Merwe");
        assertTrue("van der Merwe".equals(person.getLastName()));
        person.setAddress("200 Roach Road, Cape Town");
        assertTrue("200 Roach Road, Cape Town".equals(person.getAddress()));
        person.setTelephoneNumber("123456789");
        assertTrue("123456789".equals(person.getTelephoneNumber()));
        person.setEmailAddress("second@email.net");
        assertTrue("second@email.net".equals(person.getEmailAddress()));
    }

    public void testToString() {
        assertTrue("Person: John Smith (0002225094081)".equals(person.toString()));
    }

    public void testEquals_Null() {
        Object o = null;
        assertFalse(person.equals(o));
    }

    public void testEquals_Class() {
        Object o = new String("test");
        assertFalse(person.equals(o));
    }

    public void testEquals() {
        Person d = new Person("0002225094081", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com");
        assertTrue(person.equals(d));
    }
}
