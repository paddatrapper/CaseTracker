package com.kritsit.casetracker.client.domain.datastructures;

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
        assertTrue(staff instanceof Staff);
    }

    public void testAccessors() {
        assertTrue("testUsername".equals(staff.getUsername()));
        assertTrue("firstName".equals(staff.getFirstName()));
        assertTrue("lastName".equals(staff.getLastName()));
        assertTrue("department".equals(staff.getDepartment()));
        assertTrue(Permission.EDITOR == staff.getPermission());
    }

    public void testMutators() {
        staff.setUsername("secondUsername");
        staff.setFirstName("secondFirstName");
        staff.setLastName("secondLastName");
        staff.setDepartment("secondDepartment");
        staff.setPermission(Permission.VIEWER);

        assertTrue("secondUsername".equals(staff.getUsername()));
        assertTrue("secondFirstName".equals(staff.getFirstName()));
        assertTrue("secondLastName".equals(staff.getLastName()));
        assertTrue("secondDepartment".equals(staff.getDepartment()));
        assertTrue(Permission.VIEWER == staff.getPermission());
    }

    public void testEquals_true() {
        Staff user = new Staff("testUsername", "firstName", "lastName", "department", "position", Permission.EDITOR);
        assertTrue(user.equals(staff));
    }

    public void testEquals_false() {
        Staff user = new Staff("anotherUsername", "firstName", "lastName", "department", "position", Permission.EDITOR);
        assertFalse(user.equals(staff));
    }
}
