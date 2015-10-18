package com.kritsit.casetracker.client.domain.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.time.LocalDate;
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
        List<Appointment> appointments = new ArrayList<>();
        day = new Day(1, appointments);
    }

    public void testCreation() {
        Day d = new Day();
        Day d1 = new Day(1);
        assertTrue(d.getClass() == Day.class);
        assertTrue(d1.getClass() == Day.class);
        assertTrue(day.getClass() == Day.class);
    }

    public void testAddAppointment() {
        Appointment appointment = new Appointment(LocalDate.now(), "Meet John");
        day.addAppointment(appointment);
        assertEquals(appointment, day.getAppointments().get(0));
    }

    public void testAccessors() {
        assertEquals("1", day.getNumber());
        assertNotNull(day.getAppointments());
        assertNotNull(day.dayNumberProperty());
        assertNotNull(day.appointmentsProperty());
    }

    public void testMutators() {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment(LocalDate.now(), "Return to John's farm"));
        day.setNumber("2");
        day.setAppointments(appointments);

        assertEquals("2", day.getNumber());
        assertEquals(appointments, day.getAppointments());
    }

    public void testToString() {
        Appointment appointment = new Appointment(LocalDate.now(), "Meet John");
        day.addAppointment(appointment);
        String toString = "1\nMeet John\n";
        assertEquals(toString, day.toString());
    }

    public void testEquals_Null() {
        assertFalse(day.equals(null));
    }

    public void testEquals_Class() {
        assertFalse(day.equals("day"));
    }

    public void testEquals() {
        List<Appointment> appointments = new ArrayList<>();
        Day d = new Day(1, appointments);
        assertTrue(day.equals(d));
    }
}
