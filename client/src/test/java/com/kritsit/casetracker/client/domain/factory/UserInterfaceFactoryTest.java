package com.kritsit.casetracker.client.domain.factory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UserInterfaceFactoryTest extends TestCase {
    public UserInterfaceFactoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(UserInterfaceFactoryTest.class);
    }

    public void setUp(){}

    public void testGetEditorFrame() {
        assertTrue(UserInterfaceFactory.getEditorFrame() != null);
    }

    public void testGetAdministratorFrame() {
        assertTrue(UserInterfaceFactory.getAdministratorFrame() != null);
    }
}
