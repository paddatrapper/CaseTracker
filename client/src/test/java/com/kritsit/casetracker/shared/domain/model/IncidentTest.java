package com.kritsit.casetracker.shared.domain.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.time.LocalDate;

public class IncidentTest extends TestCase {
    Incident incident;

    public IncidentTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(IncidentTest.class);
    }

    public void setUp() {
        LocalDate date = LocalDate.parse("2015-01-19");
        LocalDate followUpDate = LocalDate.parse("2015-02-19");
        incident = new Incident("100 Long Street, Cape Town", "Western Cape", date, followUpDate, false);
    }

    public void testCreation() {
        assertTrue(incident.getClass() == Incident.class);
        Incident i = new Incident(-25.0001, 10.11, "Western Cape", LocalDate.now(), LocalDate.now(), false);
        assertTrue(i.getClass() == Incident.class);
    }

    public void testAccessors() {
        LocalDate date = LocalDate.parse("2015-01-19");
        LocalDate followUpDate = LocalDate.parse("2015-02-19");

        assertTrue("100 Long Street, Cape Town".equals(incident.getAddress()));
        assertTrue("Western Cape".equals(incident.getRegion()));
        assertTrue(date.equals(incident.getDate()));
        assertTrue(followUpDate.equals(incident.getFollowUpDate()));
        assertFalse(incident.isFollowedUp());
        assertTrue(incident.dateProperty() != null);
    }

    public void testMutators() {
        LocalDate followUpDate = LocalDate.now();
        LocalDate date = LocalDate.now();

        incident.setLongitude(-25.993);
        incident.setLatitude(10.26653);
        incident.setAddress("20 Aderly Street, Cape Town");
        incident.setRegion("Eastern Cape");
        incident.setDate(date);
        incident.setFollowUpDate(followUpDate);
        incident.setFollowedUp(true);

        assertTrue(Math.abs(-25.993 - incident.getLongitude()) < 0.00001);
        assertTrue(Math.abs(10.26653 - incident.getLatitude()) < 0.00001);
        assertTrue("20 Aderly Street, Cape Town".equals(incident.getAddress()));
        assertTrue("Eastern Cape".equals(incident.getRegion()));
        assertTrue(date.equals(incident.getDate()));
        assertTrue(followUpDate.equals(incident.getFollowUpDate()));
        assertTrue(incident.isFollowedUp());
    }

    public void testGetFollowUpDate() {
        LocalDate date = LocalDate.parse("2015-01-10");
        LocalDate followUpDate = LocalDate.parse("2015-01-17");
        
        incident.setDate(date);

        assertTrue(followUpDate.equals(incident.getDefaultFollowUpDate()));
    }

    public void testEquals_Null() {
        assertFalse(incident.equals(null));
    }

    public void testEquals_Class() {
        LocalDate date = LocalDate.now();
        assertFalse(incident.equals(date.plusDays(2)));
    }

    public void testEquals() {
        LocalDate date = LocalDate.now();
        LocalDate followUpDate = LocalDate.now();
        date = LocalDate.parse("2015-01-19");
        followUpDate = LocalDate.parse("2015-02-19");
        Incident i = new Incident("100 Long Street, Cape Town", "Western Cape", date, followUpDate, false);
        Incident differentIncident = new Incident("230 Long Street, Cape Town", "Western Cape", date, followUpDate, false);
        
        assertTrue(incident.equals(i));
        assertFalse(incident.equals(differentIncident));
    }

    public void testToString_Address() {
        String incidentString = "Incident: 100 Long Street, Cape Town (2015-01-19)";

        assertTrue(incidentString.equals(incident.toString()));
    }

    public void testToString_Coordinates() {
        String incidentString = "Incident: 20.221, -12.776 (2015-01-19)";
        LocalDate date = LocalDate.parse("2015-01-19");
        LocalDate followUpDate = LocalDate.parse("2015-02-19");
        Incident i = new Incident(20.221, -12.776, "Western Cape", date, followUpDate, false);

        System.out.println(i.toString());
        assertTrue(incidentString.equals(i.toString()));
    }
}
