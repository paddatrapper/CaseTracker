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
    }
}
