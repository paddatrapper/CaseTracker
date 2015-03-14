package com.kritsit.casetracker.shared.domain.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IncidentTest extends TestCase {
    Incident incident;

    public IncidentTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(IncidentTest.class);
    }

    public void setUp() {
        SimpleDateFormat df  = new SimpleDateFormat("yyy-MM-dd");
        try {
            Date date = df.parse("2015-01-19");
            Date followUpDate = df.parse("2015-02-19");
            incident = new Incident("100 Long Street, Cape Town", "Western Cape", date, followUpDate, false);
        } catch (ParseException ex) {
            ex.printStackTrace();
            incident = null;
        }
    }

    public void testCreation() {
        assertTrue(incident.getClass() == Incident.class);
        Incident i = new Incident(-25.0001, 10.11, "Western Cape", new Date(), new Date(), false);
        assertTrue(i.getClass() == Incident.class);
    }

    public void testAccessors() {
        SimpleDateFormat df  = new SimpleDateFormat("yyy-MM-dd");
        Date date = new Date();
        Date followUpDate = new Date();
        try {
            date = df.parse("2015-01-19");
            followUpDate = df.parse("2015-02-19");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        assertTrue("100 Long Street, Cape Town".equals(incident.getAddress()));
        assertTrue("Western Cape".equals(incident.getRegion()));
        assertTrue(date.equals(incident.getDate()));
        assertTrue(followUpDate.equals(incident.getFollowUpDate()));
        assertFalse(incident.isFollowedUp());
    }

    public void testMutators() {
        Date followUpDate = new Date();
        Date date = new Date();

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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new Date();
        Date followUpDate = new Date();
        try {
            date = df.parse("2015-01-10");
            followUpDate = df.parse("2015-01-17");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        incident.setDate(date);

        assertTrue(followUpDate.equals(incident.getDefaultFollowUpDate()));
    }

    public void testEquals_Null() {
        assertFalse(incident.equals(null));
    }

    public void testEquals_Class() {
        Date date = new Date();
        assertFalse(incident.equals(date));
    }

    public void testEquals() {
        SimpleDateFormat df  = new SimpleDateFormat("yyy-MM-dd");
        Date date = new Date();
        Date followUpDate = new Date();
        try {
            date = df.parse("2015-01-19");
            followUpDate = df.parse("2015-02-19");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        Incident i = new Incident("100 Long Street, Cape Town", "Western Cape", date, followUpDate, false);
        
        assertTrue(incident.equals(i));
    }

    public void testToString() {
        String incidentString = "Incident: 100 Long Street, Cape Town (2015-01-19)";

        assertTrue(incidentString.equals(incident.toString()));
    }
}
