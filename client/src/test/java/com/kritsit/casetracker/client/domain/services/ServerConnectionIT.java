package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.factory.ServiceFactory;
import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Evidence;
import com.kritsit.casetracker.shared.domain.model.Incident;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Staff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServerConnectionIT extends TestCase {
    String host = "localhost";
    int port = 1244;
    IConnectionService connection;

    public ServerConnectionIT(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ServerConnectionIT.class);
    }

    public void setUp() {
        connection = ServiceFactory.getServerConnection();
    }

    public void testConnection_PortOutOfBounds() {
        try {
            connection.open(host, 65555);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            assertEquals("Port must be in range", ex.getMessage());
        }
    }

    public void testConnection_UnknownHostException() {
        try {
            connection.open("ThisIsNotAValidHost", port);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            assertEquals("Host not found", ex.getMessage());
        }
    }

    public void testConnection_Succeed() {
        assertTrue(connection.open(host, port));
    }

    public void testLogin_Correct() {
        connection.open(host, port);
        assertTrue(connection.login("inspector", "inspector".hashCode()));
    }

    public void testLogin_Incorrect() {
        connection.open(host, port);
        assertFalse(connection.login("test", "".hashCode()));
    }
    
    public void testGetUser() {
        connection.open(host, port);
        assertNotNull(connection.getUser("inspector", "inspector".hashCode()));
    }

    public void testGetCases_NoUser() {
        connection.open(host, port);
        List<Case> caseList = connection.getCases(null);
        assertNotNull(caseList);
    }

    public void testGetCases_User() {
        connection.open(host, port);
        Staff user = new Staff("inspector", "test", "inspector", "department", 
                "position", Permission.EDITOR);
        List<Case> caseList = connection.getCases(user);
        assertNotNull(caseList);
    }

    public void testGetInspectors() {
        connection.open(host, port);
        assertNotNull(connection.getInspectors());
    }
    
    public void testGetLastCaseNumber() {
        connection.open(host, port);
        assertFalse("0000-00-0000".equals(connection.getLastCaseNumber()));
    }

    public void testAddCase() {
        connection.open(host, port);

        String caseNumber = connection.getLastCaseNumber();
        String[] parts = caseNumber.split("-");
        int nextNumber = Integer.parseInt(parts[2]) + 1;
        String nextSequenceNumber = String.format("%04d", nextNumber);
        caseNumber = parts[0] + "-" + parts[1] + "-" + nextSequenceNumber;

        String caseName = "test case";
        String description = "Something happened";
        String animalsInvolved = "Some animals";
        Staff investigatingOfficer = new Staff("inspector", "inspector", 
                "inspector", "department","position", Permission.EDITOR);
        LocalDate incidentDate = LocalDate.parse("2015-03-02");
        LocalDate followUpDate = LocalDate.parse("2015-03-08");
        Incident incident = new Incident(-1, "some address", "Western Cape", 
                incidentDate, followUpDate, true);
        Defendant defendant = new Defendant(-1, null, "Mr", "Test", "asd s", 
                null, null, false);
        Person complainant = new Person(-1, null, "Mrs", "Test", "sad s", null, null);
        boolean isReturnVisit = false;
        String caseType = "testing";
        File evidenceFile = new File("src/test/resources/file-test.txt");
        List<Evidence> evidence = new ArrayList<>();
        evidence.add(new Evidence(-1, "Test evidence", null, evidenceFile));

        Case c = new Case(caseNumber, caseName, description, animalsInvolved, 
            investigatingOfficer, incident, defendant, complainant, null, 
            evidence, isReturnVisit, null, caseType, null);
        assertTrue(connection.addCase(c));
    }

    public void testEditCase() {
        connection.open(host, port);

        String caseNumber = connection.getLastCaseNumber();
        String caseName = "test case";
        String description = "Something happened";
        String animalsInvolved = "Some animals";
        Staff investigatingOfficer = new Staff("inspector", "inspector", 
                "inspector", "department","position", Permission.EDITOR);
        LocalDate incidentDate = LocalDate.parse("2015-03-02");
        LocalDate followUpDate = LocalDate.parse("2015-03-08");
        Incident incident = new Incident(-1, "some address", "Western Cape", 
                incidentDate, followUpDate, true);
        Defendant defendant = new Defendant(-1, null, "Mr", "Test", "asd s", 
                null, null, false);
        Person complainant = new Person(-1, null, "Mrs", "Test", "sad s", null, null);
        boolean isReturnVisit = false;
        String caseType = "testing";
        File evidenceFile = new File("src/test/resources/file-test.txt");
        List<Evidence> evidence = new ArrayList<>();
        evidence.add(new Evidence(2, "Test evidence", null, evidenceFile));

        Case c = new Case(caseNumber, caseName, description, animalsInvolved, 
            investigatingOfficer, incident, defendant, complainant, null, 
            evidence, isReturnVisit, null, caseType, null);
        assertTrue(connection.editCase(c));
    }
    
    public void testAddUser(){
        connection.open(host, port);
        String username = "johndoe";
        String firstname = "John";
        String lastname = "Doe";
        String department = "IT";
        String position = "admin";
        Permission permission = Permission.ADMIN;
        
        Staff staff = new Staff(username, firstname, lastname, department, position, permission);
        assertTrue(connection.addUser(staff));
    }

    public void testEditUser(){
        connection.open(host, port);
        String username = "johndoe";
        String firstname = "John";
        String lastname = "Doe";
        String department = "IT";
        String position = "admin";
        Permission permission = Permission.ADMIN;
        
        Staff staff = new Staff(username, firstname, lastname, department, position, permission);
        assertTrue(connection.editUser(staff));
    }
    
    public void testDeleteUser(){
        connection.open(host, port);
        String username = "johndoe";
        assertTrue(connection.deleteUser(username));
    }
    
    public void testResetPass(){
        connection.open(host, port);
        setUpUser("johndoe", 1234);
        String username = "johndoe";
        int hashedRandomPass = 1234;
        assertTrue(connection.resetPassword(username, hashedRandomPass));
        assertTrue(connection.login(username, hashedRandomPass));
    }
    
    public void testChangePass(){
        connection.open(host, port);
        setUpUser("johndoe", 1234);
        String username = "johndoe";
        int currentHashedPass = 1234;
        int newHashedPass = 4321;
        assertTrue(connection.changePassword(username, currentHashedPass, newHashedPass));
    }

    private void setUpUser(String username, int passwordHash) {
        String firstname = "John";
        String lastname = "Doe";
        String department = "IT";
        String position = "admin";
        Permission permission = Permission.ADMIN;
        
        Staff staff = new Staff(username, firstname, lastname, department, position, permission);
        connection.addUser(staff);
        connection.resetPassword(username, passwordHash);
    }
   
    public void tearDown() throws IOException {
        ServiceFactory.resetServerConnection();
        if (connection.isOpen()) {
            connection.deleteUser("johndoe");
            connection.close();
        }
        File evidenceFile = new File("../server/data/");
        if (evidenceFile.exists()) {
            delete(evidenceFile);
        }
    }

    private void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                delete(c);
            }
        }
        if (!f.delete()) {
            throw new FileNotFoundException("Unable to delete file: " + f);
        }
    }
}
