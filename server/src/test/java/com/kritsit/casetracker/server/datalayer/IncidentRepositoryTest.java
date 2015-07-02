package com.kritsit.casetracker.server.datalayer;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.shared.domain.model.Incident;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidentRepositoryTest extends TestCase {
    private IIncidentRepository incidentRepo;
    private List<Map<String, String>> incidentList;

    public IncidentRepositoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(IncidentRepositoryTest.class);
    }

    public void setUp() {
        incidentList = new ArrayList<>();
        Map<String, String> incident = new HashMap<>();
        incident.put("address", "Byte Bridge");
        incident.put("region", "Eastern Cape");
        incident.put("incidentDate", "2015-02-05");
        incident.put("followUpDate", "2015-02-12");
        incident.put("followedUp", "1");
        incidentList.add(incident);
    }
    
    public void testGetIncidents_Address() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT incidents.* FROM incidents INNER JOIN(cases) " +
            "WHERE incidents.id=cases.incidentId AND cases.caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql, caseNumber)).thenReturn(incidentList);
        IIncidentRepository incidentRepo = new IncidentRepository(db);

        Incident incident = incidentRepo.getIncident(caseNumber);
        
        assertTrue(incident != null);
        verify(db).executeQuery(sql, caseNumber);
    }

    public void testGetIncidents_Coordinates() throws SQLException, RowToModelParseException {
        incidentList = new ArrayList<>();
        Map<String, String> i = new HashMap<>();
        i.put("longitude", "-25.001");
        i.put("latitude", "10.221");
        i.put("region", "Eastern Cape");
        i.put("incidentDate", "2015-02-05");
        i.put("followUpDate", "2015-02-12");
        i.put("followedUp", "1");
        incidentList.add(i);
        String caseNumber = "3";
        String sql = "SELECT incidents.* FROM incidents INNER JOIN(cases) " +
            "WHERE incidents.id=cases.incidentId AND cases.caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql, caseNumber)).thenReturn(incidentList);
        IIncidentRepository incidentRepo = new IncidentRepository(db);

        Incident incident = incidentRepo.getIncident(caseNumber);
        
        assertTrue(incident != null);
        verify(db).executeQuery(sql, caseNumber);
    }

    public void testGetIncidents_Null() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT incidents.* FROM incidents INNER JOIN(cases) " +
            "WHERE incidents.id=cases.incidentId AND cases.caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = new IncidentRepository(db);

        Incident incident = incidentRepo.getIncident(caseNumber);

        assertTrue(incident == null);
        verify(db).executeQuery(sql, caseNumber);
    }
    
    public void testGetIncidents_Empty() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT incidents.* FROM incidents INNER JOIN(cases) " +
            "WHERE incidents.id=cases.incidentId AND cases.caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = new IncidentRepository(db);
        when(db.executeQuery(sql, caseNumber)).thenReturn(new ArrayList<Map<String, String>>());

        Incident incident = incidentRepo.getIncident(caseNumber);
        
        assertTrue(incident == null);
        verify(db).executeQuery(sql, caseNumber);
    }

    public void testInsertIncident_Address() throws SQLException, RowToModelParseException {
        String address = "Test Address";
        String region = "Western Cape";
        LocalDate date = LocalDate.parse("2015-02-14");
        LocalDate followUpDate = LocalDate.parse("2015-02-21");
        boolean booleanIsFollowedUp = false;
        Incident incident = new Incident(address, region, date, followUpDate, booleanIsFollowedUp);
        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = new IncidentRepository(db);
        String sql = "INSERT INTO incidents VALUES (NULL, NULL, NULL, ?, ?, ?, ?, ?);";
        String isFollowedUp = incident.isFollowedUp() ? "1" : "0";

        incidentRepo.insertIncident(incident);

        verify(db).executeUpdate(sql, incident.getAddress(), incident.getRegion(), 
                incident.getDate().toString(), incident.getFollowUpDate().toString(), 
                isFollowedUp);
    }

    public void testInsertIncident_Coordinates() throws SQLException, RowToModelParseException {
        double longitude = 35.2543;
        double latitude = -12.2543;
        String region = "Western Cape";
        LocalDate date = LocalDate.parse("2015-02-14");
        LocalDate followUpDate = LocalDate.parse("2015-02-21");
        boolean booleanIsFollowedUp = false;
        Incident incident = new Incident(longitude, latitude, region, date, followUpDate, booleanIsFollowedUp);
        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = new IncidentRepository(db);
        String sql = "INSERT INTO incidents VALUES (NULL, ?, ?, NULL, ?, ?, ?, ?);";
        String isFollowedUp = incident.isFollowedUp() ? "1" : "0";

        incidentRepo.insertIncident(incident);

        verify(db).executeUpdate(sql, String.valueOf(incident.getLongitude()), 
                String.valueOf(incident.getLatitude()), incident.getRegion(), 
                incident.getDate().toString(), incident.getFollowUpDate().toString(), 
                isFollowedUp);
    }
}
