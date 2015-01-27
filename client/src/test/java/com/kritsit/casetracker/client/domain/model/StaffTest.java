package com.kritsit.casetracker.client.domain.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StaffTest extends TestCase {
    Staff staff;

    public StaffTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(StaffTest.class);
    }

    public void setUp() {
        staff = new Staff("testUsername", "firstName", "lastName", "department", "position", Permission.EDITOR);
    }

    public void testCreation() {
        assertTrue(staff.getClass() == Staff.class);
    }

    public void testAccessors() {
        assertTrue("testUsername".equals(staff.getUsername()));
        assertTrue("firstName".equals(staff.getFirstName()));
        assertTrue("lastName".equals(staff.getLastName()));
        assertTrue("department".equals(staff.getDepartment()));
        assertTrue("position".equals(staff.getPosition()));
        assertTrue(Permission.EDITOR == staff.getPermission());
    }

    public void testMutators() {
        staff.setUsername("secondUsername");
        staff.setFirstName("secondFirstName");
        staff.setLastName("secondLastName");
        staff.setDepartment("secondDepartment");
        staff.setPosition("secondPosition");
        staff.setPermission(Permission.VIEWER);

        assertTrue("secondUsername".equals(staff.getUsername()));
        assertTrue("secondFirstName".equals(staff.getFirstName()));
        assertTrue("secondLastName".equals(staff.getLastName()));
        assertTrue("secondDepartment".equals(staff.getDepartment()));
        assertTrue("secondPosition".equals(staff.getPosition()));
        assertTrue(Permission.VIEWER == staff.getPermission());
    }

    public void testEquals_Null() {
        assertFalse(staff.equals(null));
    }

    public void testEquals_Class() {
        assertFalse(staff.equals("user"));
    }

    public void testEquals_Username() {
        Staff user = new Staff("anotherUsername", "firstName", "lastName", "department", "position", Permission.EDITOR);

        assertFalse(user.equals(staff));
    }

    public void testEquals_FirstName() {
        Staff user = new Staff("testUsername", "anotherName", "lastName", "department", "position", Permission.EDITOR);

        assertFalse(user.equals(staff));
    }

    public void testEquals_LastName() {
        Staff user = new Staff("testUsername", "firstName", "anotherName", "department", "position", Permission.EDITOR);

        assertFalse(user.equals(staff));
    }

    public void testEquals_Department() {
        Staff user = new Staff("testUsername", "firstName", "lastName", "anotherDepartment", "position", Permission.EDITOR);

        assertFalse(user.equals(staff));
    }

    public void testEquals_Position() {
        Staff user = new Staff("testUsername", "firstName", "lastName", "department", "anotherPosition", Permission.EDITOR);

        assertFalse(user.equals(staff));
    }

    public void testEquals_Permission() {
        Staff user = new Staff("testUsername", "firstName", "lastName", "department", "position", Permission.VIEWER);

        assertFalse(user.equals(staff));
    }

    public void testEquals() {
        Staff user = new Staff("testUsername", "firstName", "lastName", "department", "position", Permission.EDITOR);

        assertTrue(user.equals(staff));
    }
}
