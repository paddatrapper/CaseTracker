package com.kritsit.casetracker.client.domain.datastructures;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;

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

    public void testAddVehicle() {
        Vehicle v = new Vehicle("CA123456", "Nissan", "White", false);
        defendant.addVehicle(v);
        assertTrue(v.equals(defendant.getVehicles().get(0)));
    }

    public void testAddVehicles() {
        Vehicle[] v = new Vehicle[3];
        for (int i = 0; i < v.length; i++) {
            v[i] = new Vehicle("CA123456", "Nissan", "White", false);
        }
        defendant.addVehicles(v);
        assertTrue(defendant.getVehicles().size() == v.length);
        Vehicle vehicle = new Vehicle("CA123456", "Nissan", "White", false);
        assertTrue(vehicle.equals(defendant.getVehicles().get(0)));
    }

    public void testIsSecondOffence() {
        assertFalse(defendant.isSecondOffence());
    }

    public void testSetSecondOffence() {
        defendant.setSecondOffence(true);
        assertTrue(defendant.isSecondOffence());
    }

    public void testSetVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle v = new Vehicle("CA123456", "Nissan", "White", false);
        vehicles.add(v);
        vehicles.add(v);
        defendant.setVehicles(vehicles);
        assertTrue(vehicles.equals(defendant.getVehicles()));
    }

    public void testToString() {
        assertTrue("Defendant: John Smith (0002225094081)".equals(defendant.toString()));
    }
}
