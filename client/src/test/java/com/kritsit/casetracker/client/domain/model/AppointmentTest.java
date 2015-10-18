package com.kritsit.casetracker.client.domain.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.time.LocalDate;

public class AppointmentTest extends TestCase {
    Appointment appointment;

    public AppointmentTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(AppointmentTest.class);
    }

    public void setUp() {
        appointment = new Appointment(LocalDate.now(), "Meet John");
    }

    public void testCreation() {
        assertTrue(appointment.getClass() == Appointment.class);
    }

    public void testAccessors() {
        assertNotNull(appointment.getDate());
        assertEquals("Meet John", appointment.getDetails());
    }

    public void testMutators() {
        LocalDate date = LocalDate.now();
        String details = "This are the details";

        appointment.setDate(date);
        appointment.setDetails(details);

        assertEquals(date, appointment.getDate());
        assertEquals(details, appointment.getDetails());
    }

    public void testToString() {
        LocalDate date = LocalDate.now();
        String toString = date.toString() + " - Meet John";
        assertEquals(toString, appointment.toString());
    }

    public void testEquals_Null() {
        assertFalse(appointment.equals(null));
    }

    public void testEquals_Class() {
        assertFalse(appointment.equals("appointment"));
    }

    public void testEquals() {
        Appointment testAppointment = new Appointment(LocalDate.now(), "Meet John");
        assertTrue(testAppointment.equals(appointment));
    }
}
