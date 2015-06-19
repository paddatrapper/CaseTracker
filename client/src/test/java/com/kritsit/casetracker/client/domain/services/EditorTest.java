package com.kritsit.casetracker.client.domain.services;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.client.domain.model.Appointment;
import com.kritsit.casetracker.client.domain.model.Day;
import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Incident;
import com.kritsit.casetracker.shared.domain.model.Staff;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Person;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class EditorTest extends TestCase {

    public EditorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(EditorTest.class);
    }

    public void testCreation() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        Editor editor = new Editor(user, connection);
        assertTrue(editor instanceof IEditorService);
    }

    public void testGetUser() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        Editor editor = new Editor(user, connection);
        assertTrue(editor.getUser() != null);
    }

    public void testGetCases() {
        List<Case> caseList = new ArrayList<>();
        Case c = mock(Case.class);
        caseList.add(c);
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        Editor editor = new Editor(user, connection);
       
        when(connection.getCases(null)).thenReturn(caseList);

        assertTrue(caseList.equals(editor.getCases()));
        assertTrue(caseList.equals(editor.getCases()));
        verify(connection).getCases(null);
    }

    public void testGetBlankMonth() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        Editor editor = new Editor(user, connection);
        List<List<Day>> blankMonth = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<Day> week = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                week.add(new Day());
            }
            blankMonth.add(week);
        }
        assertTrue(blankMonth.equals(editor.getBlankMonth()));
    }

    public void testGetMonthAppointments() {
        LocalDate date = LocalDate.parse("2015-02-01");
        String name = "testCase";
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = new Staff("user", "John", "Smith", "Inspectorate", "Inspector", Permission.EDITOR);
        Incident incident = mock(Incident.class);
        Defendant defendant = mock(Defendant.class);
        Person complainant = mock(Person.class);
        Case c = new Case("001/15", name, "some description", "some animals", user, incident, defendant, complainant, date, null, true, date, "type", "outcome");;
        List<Case> caseList = new ArrayList<>();
        caseList.add(c);
        IEditorService editor = new Editor(user, connection);
        List<List<Day>> monthAppointments = editor.getBlankMonth();

        int numberOfDays = 28;
        int startOfMonth = 6;

        for (int i = 1; i <= numberOfDays; i++) {
            int row = (startOfMonth + i - 1) / 7;
            int column = (startOfMonth + i - 1) % 7;
            List<Day> week = monthAppointments.get(row);
            Day day = new Day(i);
            week.set(column, day);
        }
            
        int row = (date.getDayOfWeek().getValue() - 1) / 7;
        int column = (date.getDayOfWeek().getValue() - 1) % 7;

        Day day = new Day(1);
        Appointment returnVisit = new Appointment(date, "Return visit: " + name);
        Appointment nextCourtDate = new Appointment(date, "Court date: " + name);
        day.addAppointment(returnVisit);
        day.addAppointment(nextCourtDate);
        monthAppointments.get(row).set(column, day);
        
        when(connection.getCases(null)).thenReturn(caseList);

        assertTrue(monthAppointments.equals(editor.getMonthAppointments(2, 2015)));

        verify(connection).getCases(null);
    } 

    public void testGetInspectors() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
        
        editor.getInspectors(); 

        verify(connection).getInspectors();
    }

    public void testGetCaseTypes() {
        String caseType = "TestType";
        List<String> caseTypes = new ArrayList<>();
        caseTypes.add(caseType);
        List<Case> caseList = new ArrayList<>();
        Case c = mock(Case.class);
        caseList.add(c);
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
       
        when(connection.getCases(null)).thenReturn(caseList);
        when(c.getType()).thenReturn(caseType);

        editor.getCases();

        assertTrue(caseTypes.equals(editor.getCaseTypes()));
        verify(connection).getCases(null);
        verify(c, times(3)).getType();
    }

    public void testGetDefendants() {
        Defendant defendant = mock(Defendant.class);
        List<Defendant> defendants = new ArrayList<>();
        defendants.add(defendant);
        List<Case> caseList = new ArrayList<>();
        Case c = mock(Case.class);
        caseList.add(c);
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
       
        when(connection.getCases(null)).thenReturn(caseList);
        when(c.getDefendant()).thenReturn(defendant);

        editor.getCases();

        assertTrue(defendants.equals(editor.getDefendants()));
        verify(connection).getCases(null);
        verify(c, times(3)).getDefendant();
    }

    public void testGetComplainants() {
        Person complainant = mock(Person.class);
        List<Person> complainants = new ArrayList<>();
        complainants.add(complainant);
        List<Case> caseList = new ArrayList<>();
        Case c = mock(Case.class);
        caseList.add(c);
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
       
        when(connection.getCases(null)).thenReturn(caseList);
        when(c.getComplainant()).thenReturn(complainant);

        editor.getCases();

        assertTrue(complainants.equals(editor.getComplainants()));
        verify(connection).getCases(null);
        verify(c, times(3)).getComplainant();
    }

    public void testGetNextCaseNumber() {
        String lastCaseNumber = "2015-02-0001";
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
        LocalDate today = LocalDate.now();
        String nextCaseNumber = today.getYear() + "-" + String.format("%02d", today.getMonthValue()) + "-0001";
        
        when(connection.getLastCaseNumber()).thenReturn(lastCaseNumber);

        assertTrue(nextCaseNumber.equals(editor.getNextCaseNumber()));
        verify(connection).getLastCaseNumber();
    }

    public void testAddCase_Null() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);

        InputToModelParseResult result = editor.addCase(null); 

        assertFalse(result.isSuccessful());
        assertTrue("Required information missing".equals(result.getReason()));
    }

    public void testAddCase_NoData() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);

        InputToModelParseResult result = editor.addCase(inputMap);            

        assertFalse(result.isSuccessful());
        assertTrue("Required information missing".equals(result.getReason()));
    }

    public void testAddCase_OneBlankData() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
        inputMap.put("caseNumber", "2015-02-0001");
        inputMap.put("caseName", "");

        InputToModelParseResult result = editor.addCase(inputMap);            

        assertFalse(result.isSuccessful());
        assertTrue("Case name required".equals(result.getReason()));
    }

    public void testAddCase_TwoBlankData() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
        inputMap.put("caseNumber", "2015-02-0001");
        inputMap.put("caseName", "");
        inputMap.put("caseType", "");

        InputToModelParseResult result = editor.addCase(inputMap);            

        assertFalse(result.isSuccessful());
        assertTrue("Case name and Case type required".equals(result.getReason()));
    }

    public void testAddCase_DateValidator() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
        inputMap.put("incidentDate", LocalDate.parse("2300-01-01"));

        InputToModelParseResult result = editor.addCase(inputMap);            

        assertFalse(result.isSuccessful());
        assertTrue("Incident date required".equals(result.getReason()));
    }

    public void testAddCase_OjectValidator() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
        inputMap.put("investigatingOfficer", mock(Incident.class));

        InputToModelParseResult result = editor.addCase(inputMap);            

        assertFalse(result.isSuccessful());
        assertTrue("Investigating officer required".equals(result.getReason()));
    }

    public void testAddCase_BooleanValidator() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
        inputMap.put("isReturnVisit", null);

        InputToModelParseResult result = editor.addCase(inputMap);            

        assertFalse(result.isSuccessful());
        assertTrue("Is return visit required".equals(result.getReason()));
    }

    public void testAddCase_ReturnDateValidator() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
        inputMap.put("isReturnVisit", true);
        inputMap.put("returnDate", null);

        InputToModelParseResult result = editor.addCase(inputMap);            

        assertFalse(result.isSuccessful());
        assertTrue("Return date required".equals(result.getReason()));
    }

    public void testAddCase_AddressValidator() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
        inputMap.put("address", "");
        inputMap.put("longitude", "");
        inputMap.put("latitude", "");

        InputToModelParseResult result = editor.addCase(inputMap);            

        assertFalse(result.isSuccessful());
        assertTrue("Address required".equals(result.getReason()));
    }

    public void testAddCase_DoubleValidator() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IEditorService editor = new Editor(user, connection);
        inputMap.put("address", "");
        inputMap.put("longitude", "something");
        inputMap.put("latitude", "something else");

        InputToModelParseResult result = editor.addCase(inputMap);            

        assertFalse(result.isSuccessful());
        assertTrue("Latitude and Longitude required".equals(result.getReason()));
    }
}
