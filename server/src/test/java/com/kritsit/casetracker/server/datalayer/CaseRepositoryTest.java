package com.kritsit.casetracker.server.datalayer;

import static org.mockito.Mockito.*;

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

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaseRepositoryTest extends TestCase {
    private List<Map<String, String>> caseList;

    public CaseRepositoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(CaseRepositoryTest.class);
    }

    public void setUp() {
        caseList = new ArrayList<>();
        Map<String, String> c = new HashMap<>();
        c.put("caseNumber", "1");
        c.put("reference", "test case");
        c.put("caseType", "petting farm");
        c.put("details", "a test case");
        c.put("animalsInvolved", "an animal");
        c.put("staffID", "1");
        c.put("incidentID", "1");
        c.put("defendantId", "1");
        c.put("complainantID", "1");
        c.put("returnVisit", "0");
        caseList.add(c);
    }

    public void testGetCasesNull() throws SQLException, RowToModelParseException {
        String sql = "SELECT caseNumber, reference, caseType, details, " +
                "animalsInvolved, nextCourtDate, outcome, returnVisit, returnDate " +
                "FROM cases;";

        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = mock(IIncidentRepository.class);
        IPersonRepository personRepo = mock(IPersonRepository.class);
        IUserRepository userRepo = mock(IUserRepository.class);
        IEvidenceRepository evidenceRepo = mock(IEvidenceRepository.class);

        ICaseRepository caseRepo = new CaseRepository(db, incidentRepo, personRepo, userRepo, evidenceRepo);

        List<Case> cases = caseRepo.getCases();

        assertTrue(cases == null);
        verify(db).executeQuery(sql);
    }

    public void testGetCases() throws SQLException, RowToModelParseException {
            String sql = "SELECT caseNumber, reference, caseType, details, " +
                "animalsInvolved, nextCourtDate, outcome, returnVisit, returnDate " +
                "FROM cases;";
        String caseNumber = "1";
        Incident incident = mock(Incident.class);
        Defendant defendant = mock(Defendant.class);
        Person complainant = mock(Person.class);
        Staff investigatingOfficer = mock(Staff.class);
        Evidence e = mock(Evidence.class);
        List<Evidence> evidence = new ArrayList<>();
        evidence.add(e);

        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = mock(IIncidentRepository.class);
        IPersonRepository personRepo = mock(IPersonRepository.class);
        IUserRepository userRepo = mock(IUserRepository.class);
        IEvidenceRepository evidenceRepo = mock(IEvidenceRepository.class);

        when(db.executeQuery(sql)).thenReturn(caseList);
        when(incidentRepo.getIncident(caseNumber)).thenReturn(incident);
        when(personRepo.getComplainant(caseNumber)).thenReturn(complainant);
        when(personRepo.getDefendant(caseNumber)).thenReturn(defendant);
        when(userRepo.getInvestigatingOfficer(caseNumber)).thenReturn(investigatingOfficer);
        when(evidenceRepo.getEvidence(caseNumber)).thenReturn(evidence);

        ICaseRepository caseRepo = new CaseRepository(db, incidentRepo, personRepo, userRepo, evidenceRepo);

        List<Case> cases = caseRepo.getCases();

        assertTrue(cases != null);
        verify(db).executeQuery(sql);
        verify(incidentRepo).getIncident(caseNumber);
        verify(personRepo).getComplainant(caseNumber);
        verify(personRepo).getDefendant(caseNumber);
        verify(userRepo).getInvestigatingOfficer(caseNumber);
        verify(evidenceRepo).getEvidence(caseNumber);
    }

    public void testGetCases_ForUser_Null() throws SQLException, RowToModelParseException {
        String username = "testUser";
        String sql = "SELECT caseNumber, reference, caseType, details, " +
            "animalsInvolved, nextCourtDate, outcome, returnVisit, returnDate " +
            "FROM cases INNER JOIN(staff) WHERE cases.staffId=staff.username " +
            "AND staff.username=?;";
        Staff investigatingOfficer = mock(Staff.class);

        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = mock(IIncidentRepository.class);
        IPersonRepository personRepo = mock(IPersonRepository.class);
        IUserRepository userRepo = mock(IUserRepository.class);
        IEvidenceRepository evidenceRepo = mock(IEvidenceRepository.class);

        when(investigatingOfficer.getUsername()).thenReturn(username);

        ICaseRepository caseRepo = new CaseRepository(db, incidentRepo, personRepo,
                userRepo, evidenceRepo);

        List<Case> cases = caseRepo.getCases(investigatingOfficer);

        assertTrue(cases == null);
        verify(investigatingOfficer, atLeast(2)).getUsername();
        verify(db).executeQuery(sql, username);
    }

    public void testGetCases_ForUser() throws SQLException, RowToModelParseException {
        String username = "testUser";
        String caseNumber = "1";
        String sql = "SELECT caseNumber, reference, caseType, details, " +
            "animalsInvolved, nextCourtDate, outcome, returnVisit, returnDate " +
            "FROM cases INNER JOIN(staff) WHERE cases.staffId=staff.username " +
            "AND staff.username=?;";
        Incident incident = mock(Incident.class);
        Defendant defendant = mock(Defendant.class);
        Person complainant = mock(Person.class);
        Staff investigatingOfficer = mock(Staff.class);
        Evidence e = mock(Evidence.class);
        List<Evidence> evidence = new ArrayList<>();
        evidence.add(e);

        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = mock(IIncidentRepository.class);
        IPersonRepository personRepo = mock(IPersonRepository.class);
        IUserRepository userRepo = mock(IUserRepository.class);
        IEvidenceRepository evidenceRepo = mock(IEvidenceRepository.class);

        when(investigatingOfficer.getUsername()).thenReturn(username);
        when(db.executeQuery(sql, username)).thenReturn(caseList);
        when(incidentRepo.getIncident(caseNumber)).thenReturn(incident);
        when(personRepo.getComplainant(caseNumber)).thenReturn(complainant);
        when(personRepo.getDefendant(caseNumber)).thenReturn(defendant);
        when(userRepo.getInvestigatingOfficer(caseNumber)).thenReturn(investigatingOfficer);
        when(evidenceRepo.getEvidence(caseNumber)).thenReturn(evidence);

        ICaseRepository caseRepo = new CaseRepository(db, incidentRepo, personRepo,
                userRepo, evidenceRepo);

        List<Case> cases = caseRepo.getCases(investigatingOfficer);

        assertTrue(cases != null);
        verify(investigatingOfficer, atLeast(2)).getUsername();
        verify(db).executeQuery(sql, username);
        verify(incidentRepo).getIncident(caseNumber);
        verify(personRepo).getComplainant(caseNumber);
        verify(personRepo).getDefendant(caseNumber);
        verify(userRepo).getInvestigatingOfficer(caseNumber);
        verify(evidenceRepo).getEvidence(caseNumber);
    }

    public void testGetLastCaseNumber_Null() throws SQLException, RowToModelParseException {
        String sql = "SELECT caseNumber FROM cases ORDER BY caseNumber DESC LIMIT 1;";

        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = mock(IIncidentRepository.class);
        IPersonRepository personRepo = mock(IPersonRepository.class);
        IUserRepository userRepo = mock(IUserRepository.class);
        IEvidenceRepository evidenceRepo = mock(IEvidenceRepository.class);

        ICaseRepository caseRepo = new CaseRepository(db, incidentRepo, personRepo,
                userRepo, evidenceRepo);

        assertTrue("0000-00-0000".equals(caseRepo.getLastCaseNumber()));
        verify(db).executeQuery(sql);
    }

    public void testGetLastCaseNumber() throws SQLException, RowToModelParseException {
        String sql = "SELECT caseNumber FROM cases ORDER BY caseNumber DESC LIMIT 1;";
        String caseNumber = "2015-02-0001";
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> caseNumbers = new ArrayList<>();
        map.put("caseNumber", caseNumber);
        caseNumbers.add(map);

        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = mock(IIncidentRepository.class);
        IPersonRepository personRepo = mock(IPersonRepository.class);
        IUserRepository userRepo = mock(IUserRepository.class);
        IEvidenceRepository evidenceRepo = mock(IEvidenceRepository.class);

        when(db.executeQuery(sql)).thenReturn(caseNumbers);

        ICaseRepository caseRepo = new CaseRepository(db, incidentRepo, personRepo,
                userRepo, evidenceRepo);

        assertTrue(caseNumber.equals(caseRepo.getLastCaseNumber()));
        verify(db).executeQuery(sql);
    }

    public void testInsertCase() throws SQLException, RowToModelParseException {
        String sql = "INSERT INTO cases VALUES(?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, ?);";

        String caseNumber = "2015-02-0001";
        String caseName = "test case";
        String description = "Something happened";
        String animalsInvolved = "Some animals";
        Staff investigatingOfficer = new Staff("inspector", "inspector",
                "inspector", "department","position", Permission.EDITOR);
        LocalDate incidentDate = LocalDate.parse("2015-03-02");
        LocalDate followUpDate = LocalDate.parse("2015-03-08");
        Incident incident = new Incident(1, "Some address", "Western Cape", 
                incidentDate, followUpDate, true);
        Defendant defendant = new Defendant(1, null, "Mr", "Test", "some address", 
                null, null, false);
        Person complainant = new Person(1, null, "Mrs", "Test", "sad s", null, 
                null);
        boolean isReturnVisit = false;
        String strIsReturnVisit = isReturnVisit ? "1" : "0";
        String caseType = "testing";
        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = mock(IIncidentRepository.class);
        IPersonRepository personRepo = mock(IPersonRepository.class);
        IUserRepository userRepo = mock(IUserRepository.class);
        IEvidenceRepository evidenceRepo = mock(IEvidenceRepository.class);
        ICaseRepository caseRepo = new CaseRepository(db, incidentRepo, personRepo,
                userRepo, evidenceRepo);

        Case c = new Case(caseNumber, caseName, description, animalsInvolved,
                investigatingOfficer, incident, defendant, complainant, null,
                null, isReturnVisit, null, caseType, null);

        caseRepo.insertCase(c);

        verify(db).executeUpdate(sql, c.getNumber(), c.getName(), c.getType(),
                c.getDescription(), c.getAnimalsInvolved(),
                investigatingOfficer.getUsername(), 
                String.valueOf(incident.getIndexId()),
                String.valueOf(defendant.getIndexId()), 
                String.valueOf(complainant.getIndexId()), null, 
                null, strIsReturnVisit, null);
        verify(incidentRepo).insertIncident(incident);
        verify(personRepo).insertDefendant(defendant);
        verify(personRepo).insertComplainant(complainant);
    }

    public void testUpdateCase() throws SQLException, RowToModelParseException {
        String sql = "UPDATE cases SET reference=?, caseType=?, details=?, " +
            "animalsInvolved=?, staffID=?, nextCourtDate=?, outcome=?, " +
            "returnVisit=?, returnDate=? WHERE caseNumber=?;";
        String caseNumber = "2015-02-0001";
        String caseName = "test case";
        String description = "Something happened";
        String animalsInvolved = "Some animals";
        Staff investigatingOfficer = new Staff("inspector", "inspector",
                "inspector", "department","position", Permission.EDITOR);
        LocalDate incidentDate = LocalDate.parse("2015-03-02");
        LocalDate followUpDate = LocalDate.parse("2015-03-08");
        Incident incident = new Incident(1, "Some address", "Western Cape", 
                incidentDate, followUpDate, true);
        Defendant defendant = new Defendant(1, null, "Mr", "Test", "some address", 
                null, null, false);
        Person complainant = new Person(1, null, "Mrs", "Test", "sad s", null, 
                null);
        boolean isReturnVisit = false;
        String strIsReturnVisit = isReturnVisit ? "1" : "0";
        String caseType = "testing";
        IPersistenceService db = mock(IPersistenceService.class);
        IIncidentRepository incidentRepo = mock(IIncidentRepository.class);
        IPersonRepository personRepo = mock(IPersonRepository.class);
        IUserRepository userRepo = mock(IUserRepository.class);
        IEvidenceRepository evidenceRepo = mock(IEvidenceRepository.class);
        ICaseRepository caseRepo = new CaseRepository(db, incidentRepo, personRepo,
                userRepo, evidenceRepo);

        Case c = new Case(caseNumber, caseName, description, animalsInvolved,
                investigatingOfficer, incident, defendant, complainant, null,
                null, isReturnVisit, null, caseType, null);

        caseRepo.updateCase(c);

        verify(db).executeUpdate(sql, c.getName(), c.getType(),
                c.getDescription(), c.getAnimalsInvolved(),
                investigatingOfficer.getUsername(), null, null, strIsReturnVisit,
                null, c.getNumber());
        verify(incidentRepo).updateIncident(incident);
    }
}
