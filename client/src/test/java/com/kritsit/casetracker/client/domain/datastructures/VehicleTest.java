package com.kritsit.casetracker.client.domain.datastructures;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class VehicleTest extends TestCase {
    Vehicle vehicle;

    public VehicleTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(VehicleTest.class);
    }

    public void setUp() {
        vehicle = new Vehicle("CA456789", "Nissan", "White", false);
    }

    public void testAccessors() {
        assertTrue("CA456789".equals(vehicle.getRegistration()));
        assertTrue("Nissan".equals(vehicle.getMake()));
        assertTrue("White".equals(vehicle.getColour()));
        assertFalse(vehicle.isTrailer());
    }

    public void testMutators() {
        vehicle.setRegistration("CAW12345");
        assertTrue("CAW12345".equals(vehicle.getRegistration()));
        vehicle.setMake("Toyota");
        assertTrue("Toyota".equals(vehicle.getMake()));
        vehicle.setColour("Red");
        assertTrue("Red".equals(vehicle.getColour()));
        vehicle.setTrailer(true);
        assertTrue(vehicle.isTrailer());
    }

    public void testToString() {
        assertTrue("Vehicle: White Nissan (CA456789)".equals(vehicle.toString()));
    }
}
