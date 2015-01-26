package com.kritsit.casetracker.client.domain.datastructures;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        testCase.setAnimalsInvolved("1 cat");
        assertTrue("1 cat".equals(testCase.getAnimalsInvolved()));
        testCase.setNumber("112/15");
        assertTrue("112/15".equals(testCase.getNumber()));
        testCase.setName("SPCA vs someone else");
        assertTrue("SPCA vs someone else".equals(testCase.getName()));
        testCase.setType("Dog fighting");
        assertTrue("Dog fighting".equals(testCase.getType()));
        Person complainant = new Person("000222507645", "Sam", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com");
        testCase.setComplainant(complainant);
        assertTrue(complainant.equals(testCase.getComplainant()));
        testCase.setDescription("A dog fight");
        assertTrue("A dog fight".equals(testCase.getDescription()));
        Incident incident = new Incident("100 Long Street, Cape Town", "Eastern Cape", date, date, false);
        testCase.setIncident(incident);
        assertTrue(incident.equals(testCase.getIncident()));
        Staff investigatingOfficer = new Staff("user", "Another", "User", "Inspectorate", "Manager", null); 
        testCase.setInvestigatingOfficer(investigatingOfficer);
        assertTrue(investigatingOfficer.equals(testCase.getInvestigatingOfficer()));
        Date d = new Date();
        testCase.setNextCourtDate(d);
        assertTrue(d.equals(testCase.getNextCourtDate()));
        Defendant defendant = new Defendant("0002225094573", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com", true);
        testCase.setDefendant(defendant);
        assertTrue(defendant.equals(testCase.getDefendant()));
        d = new Date();
        testCase.setReturnDate(d);
        assertTrue(d.equals(testCase.getReturnDate()));
        testCase.setRuling("Not guilty");
        assertTrue("Not guilty".equals(testCase.getRuling()));
        List<Evidence> evidence = new ArrayList<>();
        evidence.add(new Evidence("A test evidence file", new File("server.file"), new File("local.file")));
        testCase.setEvidence(evidence);
        assertTrue(evidence.equals(testCase.getEvidence()));
    }

    public void testToString() {
        String toString = "Case: 1234/15 (SPCA vs Defendant)";
        assertTrue(toString.equals(testCase.toString()));
    }

    public void testEquals_Null() {
        assertFalse(testCase.equals(null));
    }

    public void testEquals_Class() {
        assertFalse(testCase.equals("testCase"));
    }

    public void testEquals() {
        date = new Date();
        Incident incident = new Incident("100 Long Street, Cape Town", "Western Cape", date, date, false);
        Defendant defendant = new Defendant("0002225094081", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com", false);
        Person complainant = new Person("000222507686", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com");
        Staff investigatingOfficer = new Staff("testUser", "Test", "User", "Inspectorate", "Manager", null); 
        List<Evidence> evidence = new ArrayList<>();
        evidence.add(new Evidence("A test evidence file", new File("server.file"), new File("local.file")));
        Case c = new Case("1234/15", "SPCA vs Defendant", "3 dogs kept locked up", "3 dogs", investigatingOfficer, incident, defendant, complainant, date, evidence, false, null, "Malnutrition", "R4500 fine");
        assertTrue(testCase.equals(c));
    }
}
