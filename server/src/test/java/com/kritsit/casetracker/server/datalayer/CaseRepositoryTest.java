package com.kritsit.casetracker.server.datalayer;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Evidence;
import com.kritsit.casetracker.shared.domain.model.Incident;
import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Staff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaseRepositoryTest extends TestCase {
    private ICaseRepository caseRepo;
    private List<Map<String, String>> caseList;
    private List<Map<String, String>> incidentList;
    private List<Map<String, String>> defendantList;
    private List<Map<String, String>> complainantList;

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
    
    public void testGetCases() throws SQLException, RowToModelParseException {
            String sql = "SELECT caseNumber, reference, caseType, details, animalsInvolved, nextCourtDate, outcome, returnVisit, returnDate FROM cases;";
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
}
