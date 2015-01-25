package com.kritsit.casetracker.client.domain.datastructures;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaseTest extends TestCase {
    Case testCase;
    Date date;

    public CaseTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(CaseTest.class);
    }

    public void setUp() {
        date = new Date();
        Incident incident = new Incident("100 Long Street, Cape Town", "Western Cape", date, date, false);
        Defendant defendant = new Defendant("0002225094081", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com", false);
        Person complainant = new Person("000222507686", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com");
        Staff investigatingOfficer = new Staff("testUser", "Test", "User", "Inspectorate", "Manager", null); 
        List<Evidence> evidence = new ArrayList<>();
        evidence.add(new Evidence("A test evidence file", new File("server.file"), new File("local.file")));
        testCase = new Case("1234/15", "SPCA vs Defendant", "3 dogs kept locked up", "3 dogs", investigatingOfficer, incident, defendant, complainant, date, evidence, false, null, "Malnutrition", "R4500 fine");
    }

    public void testCreation() {
        assertTrue(testCase.getClass() == Case.class);
    }


    public void testAddEvidence() {
        Evidence e = new Evidence("Second piece of evidence", new File("second.file"), new File("third.file"));
        testCase.addEvidence(e);
        assertTrue(e.equals(testCase.getEvidence().get(1)));
    }

    public void testAccessors() {
        assertTrue("3 dogs".equals(testCase.getAnimalsInvolved()));
        assertTrue("1234/15".equals(testCase.getNumber()));
        assertTrue("SPCA vs Defendant".equals(testCase.getName()));
        Person complainant = new Person("000222507686", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com");
        assertTrue(complainant.equals(testCase.getComplainant()));
        assertTrue("3 dogs kept locked up".equals(testCase.getDescription()));
        Incident incident = new Incident("100 Long Street, Cape Town", "Western Cape", date, date, false);
        assertTrue(incident.equals(testCase.getIncident()));
        List<Evidence> evidence = new ArrayList<>();
        evidence.add(new Evidence("A test evidence file", new File("server.file"), new File("local.file")));
        assertTrue(evidence.equals(testCase.getEvidence()));
        Staff investigatingOfficer = new Staff("testUser", "Test", "User", "Inspectorate", "Manager", null); 
        assertTrue(investigatingOfficer.equals(testCase.getInvestigatingOfficer()));
        assertTrue(date.equals(testCase.getNextCourtDate()));
        Defendant defendant = new Defendant("0002225094081", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com", false);
        assertTrue(defendant.equals(testCase.getDefendant()));
        assertFalse(testCase.isReturnVisit());
        assertTrue(null == testCase.getReturnDate());
        assertTrue("Malnutrition".equals(testCase.getType()));
        assertTrue("R4500 fine".equals(testCase.getRuling()));
    }

    public void testMutators() {
    }

    public void testToString() {
    }

    public void testEquals_Null() {
    }
}
