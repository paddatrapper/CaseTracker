package com.kritsit.casetracker.shared.domain.model;

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
        vehicle.setMake("Toyota");
        vehicle.setColour("Red");
        vehicle.setTrailer(true);

        assertTrue("CAW12345".equals(vehicle.getRegistration()));
        assertTrue("Toyota".equals(vehicle.getMake()));
        assertTrue("Red".equals(vehicle.getColour()));
        assertTrue(vehicle.isTrailer());
    }

    public void testToString() {
        assertTrue("Vehicle: White Nissan (CA456789)".equals(vehicle.toString()));
    }

    public void testEquals_Null() {
        assertFalse(vehicle.equals(null));
    }

    public void testEquals_Class() {
        assertFalse(vehicle.equals("testVehicle"));
    }

    public void testEquals() {
        Vehicle v = new Vehicle("CA456789", "Nissan", "White", false);

        assertTrue(vehicle.equals(v));
    }
}
