package com.kritsit.casetracker.server.datalayer;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Evidence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvidenceRepositoryTest extends TestCase {
    private IEvidenceRepository evidenceRepo;
    private List<Map<String, String>> evidenceList;

    public EvidenceRepositoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(EvidenceRepositoryTest.class);
    }

    public void setUp() {
        evidenceList = new ArrayList<>();
        Map<String, String> evidence = new HashMap<>();
        evidence.put("description", "test");
        evidence.put("fileLocation", "/test/file.ext");
        evidenceList.add(evidence);
    }
    
    public void testGetEvidence() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT description, fileLocation FROM evidence INNER JOIN(cases) WHERE evidence.id=cases.evidenceId AND cases.caseNumber=\'" + caseNumber + "\';";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql)).thenReturn(evidenceList);
        Case c = mock(Case.class);
        when(c.getNumber()).thenReturn(caseNumber);
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db);

        List<Evidence> evidence = evidenceRepo.getEvidence(c);

        assertTrue(evidence != null);
        verify(c, atLeast(2)).getNumber();
        verify(db).executeQuery(sql);
    }

    public void testGetEvidence_Null() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT description, fileLocation FROM evidence INNER JOIN(cases) WHERE evidence.id=cases.evidenceId AND cases.caseNumber=\'" + caseNumber + "\';";
        IPersistenceService db = mock(IPersistenceService.class);
        Case c = mock(Case.class);
        when(c.getNumber()).thenReturn(caseNumber);
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db);

        List<Evidence> evidence = evidenceRepo.getEvidence(c);

        assertTrue(evidence == null);
        verify(c, atLeast(2)).getNumber();
        verify(db).executeQuery(sql);
    }

    public void testGetEvidence_Empty() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT description, fileLocation FROM evidence INNER JOIN(cases) WHERE evidence.id=cases.evidenceId AND cases.caseNumber=\'" + caseNumber + "\';";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql)).thenReturn(new ArrayList<Map<String, String>>());
        Case c = mock(Case.class);
        when(c.getNumber()).thenReturn(caseNumber);
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db);

        List<Evidence> evidence = evidenceRepo.getEvidence(c);

        assertTrue(evidence == null);
        verify(c, atLeast(2)).getNumber();
        verify(db).executeQuery(sql);
    }
}
