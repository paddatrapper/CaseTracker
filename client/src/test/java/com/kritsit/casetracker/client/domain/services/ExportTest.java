package com.kritsit.casetracker.client.domain.services;

import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Files;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Evidence;
import com.kritsit.casetracker.shared.domain.model.Incident;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Staff;

public class ExportTest extends TestCase {

    public ExportTest(String name){
        super(name);
    }
    
    public static Test suite(){
        return new TestSuite(ExportTest.class);
    }
    
    public void testExport(){
        IExportService exportService = new Export();
        List<String> headers = new ArrayList<String>();
        headers.add("first column");
        headers.add("second column");
        List<String[]> rows = new ArrayList<String[]>();
        String[] firstRow = new String[2];
        firstRow[0] = "first row, first column";
        firstRow[1] = "first row, second column";
        rows.add(firstRow);
        String[] secondRow = new String[2];
        secondRow[0] = "second row, first column";
        secondRow[1] = "second row, second column";
        rows.add(secondRow);
        File file = new File("test.pdf");
        exportService.exportToPDF(headers, rows, file);
        assertTrue(file.exists());
        try{
            file.delete();
        }
        catch(Exception e){
            fail();
        } 
        assertFalse(file.exists());
    }  
    
    public void testExportCase(){
    	IExportService exportService = new Export();
    	
    	Staff user = mock(Staff.class);
		String caseNumber = "2015-02-0001";
		String caseName = "Developers vs Testing";
		String caseType = "Battle to the death";
		String details = "Last man standing survives";
		String animalsInvolved = "1 Developer and 1 AI";
		LocalDate incidentDate = LocalDate.parse("2014-05-14");
		String address = "";
		double longitude = -12.9880;
		double latitude = 9.82203;
		String region = "Outer space";
		boolean isReturnVisit = false;
		List<Evidence> evidence = new ArrayList<>();
		Staff investigatingOfficer = new Staff("inspector", "test", "inspector", "department", "position", Permission.EDITOR);
		Person complainant = new Person(-1, "0212202", "test", "complainant", "Somewhere", "0299222", "test@test.com");
		Defendant defendant = new Defendant(-1, "0212202", "test", "complainant", "Somewhere", "0299222", "test@test.com", false);
		Incident incident = new Incident(-1, longitude, latitude, region, incidentDate, Incident.getDefaultFollowUpDate(incidentDate), false);
		Case c = new Case(caseNumber, caseName, details, animalsInvolved, user, incident, defendant, complainant, null, evidence, isReturnVisit, null, caseType, null);

    	File file = new File("test2.pdf");
        exportService.exportCaseToPDF(c, file);
        assertTrue(file.exists());
        try{
            file.delete();
        }
        catch(Exception e){
            fail();
        } 
        assertFalse(file.exists()); 
    }
    
}