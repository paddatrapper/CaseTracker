package com.kritsit.casetracker.client.domain.datastructures;

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
        assertTrue(incident instanceof Incident);
    }

    public void testAccessors() {
        assertTrue("100 Long Street, Cape Town".equals(incident.getAddress()));
        assertTrue("Western Cape".equals(incident.getRegion()));
        SimpleDateFormat df  = new SimpleDateFormat("yyy-MM-dd");
        try {
            Date date = df.parse("2015-01-19");
            Date followUpDate = df.parse("2015-02-19");
            assertTrue(date.equals(incident.getDate()));
            assertTrue(followUpDate.equals(incident.getFollowUpDate()));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        assertFalse(incident.isFollowedUp());
    }

    public void testMutators() {
        Date followUpDate = new Date();
        incident.setAddress("20 Aderly Street, Cape Town");
        assertTrue("20 Aderly Street, Cape Town".equals(incident.getAddress()));
        incident.setRegion("Eastern Cape");
        assertTrue("Eastern Cape".equals(incident.getRegion()));
        Date date = new Date();
        incident.setDate(date);
        assertTrue(date.equals(incident.getDate()));
        try {
            incident.setFollowUpDate(followUpDate);
        } catch (IllegalArgumentException ex) {
            assertTrue("Follow up date is before incident date".equals(ex.getMessage()));
        }
        followUpDate = new Date();
        incident.setFollowUpDate(followUpDate);
        assertTrue(followUpDate.equals(incident.getFollowUpDate()));
        incident.setFollowedUp(true);
        assertTrue(incident.isFollowedUp());
    }

    public void testEquals_Null() {
        assertFalse(incident.equals(null));
    }

    public void testEquals_Class() {
        Date d = new Date();
        assertFalse(incident.equals(d));
    }

    public void testEquals_Address() {
        SimpleDateFormat df  = new SimpleDateFormat("yyy-MM-dd");
        try {
            Date date = df.parse("2015-01-19");
            Date followUpDate = df.parse("2015-02-19");
            Incident i = new Incident("20 Aderly Street, Cape Town", "Western Cape", date, followUpDate, false);
            assertFalse(incident.equals(i));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public void testEquals_Region() {
        SimpleDateFormat df  = new SimpleDateFormat("yyy-MM-dd");
        try {
            Date date = df.parse("2015-01-19");
            Date followUpDate = df.parse("2015-02-19");
            Incident i = new Incident("100 Long Street, Cape Town", "Eastern Cape", date, followUpDate, false);
            assertFalse(incident.equals(i));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public void testEquals_Date() {
        SimpleDateFormat df  = new SimpleDateFormat("yyy-MM-dd");
        try {
            Date date = df.parse("2014-01-19");
            Date followUpDate = df.parse("2015-02-19");
            Incident i = new Incident("100 Long Street, Cape Town", "Western Cape", date, followUpDate, false);
            assertFalse(incident.equals(i));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public void testEquals() {
        SimpleDateFormat df  = new SimpleDateFormat("yyy-MM-dd");
        try {
            Date date = df.parse("2015-01-19");
            Date followUpDate = df.parse("2015-02-19");
            Incident i = new Incident("100 Long Street, Cape Town", "Western Cape", date, followUpDate, false);
            assertTrue(incident.equals(i));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public void testToString() {
        String incidentString = "Incident: 100 Long Street, Cape Town (2015-01-19)";
        assertTrue(incidentString.equals(incident.toString()));
    }
}
