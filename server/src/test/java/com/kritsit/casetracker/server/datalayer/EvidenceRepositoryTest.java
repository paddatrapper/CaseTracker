package com.kritsit.casetracker.server.datalayer;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Evidence;
import com.kritsit.casetracker.shared.domain.FileSerializer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvidenceRepositoryTest extends TestCase {
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
        String sql = "SELECT description, fileLocation FROM evidence WHERE caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql, caseNumber)).thenReturn(evidenceList);
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db, null);

        List<Evidence> evidence = evidenceRepo.getEvidence(caseNumber);

        assertTrue(evidence != null);
        verify(db).executeQuery(sql, caseNumber);
    }

    public void testGetEvidence_Null() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT description, fileLocation FROM evidence WHERE caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db, null);

        List<Evidence> evidence = evidenceRepo.getEvidence(caseNumber);

        assertTrue(evidence == null);
        verify(db).executeQuery(sql, caseNumber);
    }

    public void testGetEvidence_Empty() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT description, fileLocation FROM evidence WHERE caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql, caseNumber)).thenReturn(new ArrayList<Map<String, String>>());
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db, null);

        List<Evidence> evidence = evidenceRepo.getEvidence(caseNumber);

        assertTrue(evidence == null);
        verify(db).executeQuery(sql, caseNumber);
    }

    public void testInsertEvidence() throws SQLException, IOException, RowToModelParseException{
        String caseNumber = "1";
        String description = "test evidence";
        File localFile = new File("src/test/resources/file-test.txt");
        File serverFile = new File("data/evidence/1/test_evidence.txt");
        String sql = "INSERT INTO evidence VALUES(NULL, ?, ?, ?);";
        
        Evidence evidence = new Evidence(description, null, localFile);
        evidence.setByteFile(localFile);
        IPersistenceService db = mock(IPersistenceService.class);
        FileSerializer serializer = mock(FileSerializer.class);
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db, serializer);
        
        evidenceRepo.insertEvidence(evidence, caseNumber);

        verify(db).executeUpdate(sql, evidence.getServerFileLocation(), 
                    evidence.getDescription(), caseNumber);
        verify(serializer).write(serverFile, evidence.getByteFile());
    }
}
