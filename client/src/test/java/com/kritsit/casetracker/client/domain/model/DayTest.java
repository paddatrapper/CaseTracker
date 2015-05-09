package com.kritsit.casetracker.client.domain.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;

public class DayTest extends TestCase {
    Day day;

    public DayTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(DayTest.class);
    }

    public void setUp() {
        List<String> appointments = new ArrayList<>();
        day = new Day(1, appointments);
    }

    public void testCreation() {
        Day d = new Day(1);
        assertTrue(d.getClass() == Day.class);
        assertTrue(day.getClass() == Day.class);
    }

    public void testAddAppointment() {
        String appointment = "Return to John's farm";
        day.addAppointment(appointment);
        assertTrue(appointment.equals(day.getAppointments().get(0)));
    }

    public void testAccessors() {
        assertTrue(day.getNumber() == 1);
        assertTrue(day.getAppointments() != null);
        assertTrue(day.dayNumberProperty() != null);
        assertTrue(day.appointmentsProperty() != null);
    }

    public void testMutators() {
        List<String> appointments = new ArrayList<>();
        appointments.add("Return to John's farm");
        day.setNumber(2);
        day.setAppointments(appointments);

        assertTrue(day.getNumber() == 2);
        assertTrue(appointments.equals(day.getAppointments()));
    }

    public void testToString() {
        String appointment = "Return to John's farm";
        day.addAppointment(appointment);
        String toString = "1\nReturn to John's farm\n";
        assertTrue(toString.equals(day.toString()));
    }

    public void testEquals_Null() {
        assertFalse(day.equals(null));
    }

    public void testEquals_Class() {
        assertFalse(day.equals("day"));
    }

    public void testEquals() {
        List<String> appointments = new ArrayList<>();
        Day d = new Day(1, appointments);
        assertTrue(day.equals(d));
    }
}
