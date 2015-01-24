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

    public CaseTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(CaseTest.class);
    }

    public void setUp() {
        Incident incident = new Incident("100 Long Street, Cape Town", "Western Cape", new Date(), new Date(), false);
        Defendant defendant = new Defendant("0002225094081", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com", false);
        Person complainant = new Person("000222507686", "John", "Smith", "20 Church Road, Cape Town", "0211234567", "email@address.com");
        Staff investigatingOfficer = new Staff("testUser", "Test", "User", "Inspectorate", "Manager", null); 
        List<Evidence> evidence = new ArrayList<>();
        evidence.add(new Evidence("A test evidence file", new File("server.file"), new File("local.file")));
        testCase = new Case("1234/15", "SPCA vs Defendant", "3 dogs kept locked up", "3 dogs", investigatingOfficer, incident, defendant, complainant, new Date(), evidence, false, null, "Malnutrition");
    }

    public void testCreation() {
        assertTrue(testCase.getClass() == Case.class);
    }
}
