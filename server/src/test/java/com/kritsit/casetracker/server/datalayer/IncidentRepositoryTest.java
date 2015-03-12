package com.kritsit.casetracker.server.datalayer;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.shared.domain.model.Incident;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.SQLException;
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
        String sql = "SELECT incidents.* FROM incidents INNER JOIN(cases) WHERE incidents.id=cases.incidentId AND cases.caseNumber='" + caseNumber + "';";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql)).thenReturn(incidentList);
        IIncidentRepository incidentRepo = new IncidentRepository(db);

        Incident incident = incidentRepo.getIncident(caseNumber);
        
        assertTrue(incident != null);
        verify(db).executeQuery(sql);
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
        String sql = "SELECT incidents.* FROM incidents INNER JOIN(cases) WHERE incidents.id=cases.incidentId AND cases.caseNumber='" + caseNumber + "';";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql)).thenReturn(incidentList);
        IIncidentRepository incidentRepo = new IncidentRepository(db);

        Incident incident = incidentRepo.getIncident(caseNumber);
        
        assertTrue(incident != null);
        verify(db).executeQuery(sql);
    }

    public void testGetIncidents_Null() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT incidents.* FROM incidents INNER JOIN(cases) WHERE incidents.id=cases.incidentId AND cases.caseNumber='" + caseNumber + "';";
        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = new IncidentRepository(db);

        Incident incident = incidentRepo.getIncident(caseNumber);

        assertTrue(incident == null);
        verify(db).executeQuery(sql);
    }
    
    public void testGetIncidents_Empty() throws SQLException, RowToModelParseException{
        String caseNumber = "1";
        String sql = "SELECT incidents.* FROM incidents INNER JOIN(cases) WHERE incidents.id=cases.incidentId AND cases.caseNumber='" + caseNumber + "';";
        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = new IncidentRepository(db);
        when(db.executeQuery(sql)).thenReturn(new ArrayList<Map<String, String>>());

        Incident incident = incidentRepo.getIncident(caseNumber);
        
        assertTrue(incident == null);
        verify(db).executeQuery(sql);
    }
}
