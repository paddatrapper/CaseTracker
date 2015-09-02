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
        evidence.put("id", "1");
        evidence.put("description", "test");
        evidence.put("fileLocation", "/test/file.ext");
        evidenceList.add(evidence);
    }
    
    public void testGetEvidence() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT id, description, fileLocation FROM evidence WHERE caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql, caseNumber)).thenReturn(evidenceList);
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db, null);

        List<Evidence> evidence = evidenceRepo.getEvidence(caseNumber);

        assertTrue(evidence != null);
        verify(db).executeQuery(sql, caseNumber);
    }

    public void testGetEvidence_Null() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT id, description, fileLocation FROM evidence WHERE caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db, null);

        List<Evidence> evidence = evidenceRepo.getEvidence(caseNumber);

        assertTrue(evidence == null);
        verify(db).executeQuery(sql, caseNumber);
    }

    public void testGetEvidence_Empty() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT id, description, fileLocation FROM evidence WHERE caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql, caseNumber)).thenReturn(new ArrayList<Map<String, String>>());
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db, null);

        List<Evidence> evidence = evidenceRepo.getEvidence(caseNumber);

        assertTrue(evidence == null);
        verify(db).executeQuery(sql, caseNumber);
    }

    public void testInsertEvidence() throws SQLException, IOException, RowToModelParseException{
        int id = 1;
        String caseNumber = "1";
        String description = "test evidence";
        File localFile = new File("src/test/resources/file-test.txt");
        File serverFile = new File("data/evidence/1/test_evidence.txt");
        String sql = "INSERT INTO evidence VALUES(NULL, ?, ?, ?);";
        
        Evidence evidence = new Evidence(id, description, null, localFile);
        evidence.setByteFile(localFile);
        IPersistenceService db = mock(IPersistenceService.class);
        FileSerializer serializer = mock(FileSerializer.class);
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db, serializer);
        
        evidenceRepo.insertEvidence(evidence, caseNumber);

        verify(db).executeUpdate(sql, evidence.getServerFileLocation(), 
                    evidence.getDescription(), caseNumber);
        verify(serializer).write(serverFile, evidence.getByteFile());
    }

    public void testUpdateEvidence() throws SQLException, IOException, RowToModelParseException{
        int id = 2;
        String caseNumber = "1";
        String description = "test evidence";
        File localFile = new File("src/test/resources/file-test.txt");
        File serverFile = new File("data/evidence/1/test_evidence.txt");

        String select = "SELECT id, description, fileLocation FROM evidence WHERE caseNumber=?;";
        String insert = "INSERT INTO evidence VALUES(NULL, ?, ?, ?);";
        String delete = "DELETE FROM evidence WHERE id=?;";
        
        Evidence evidence = new Evidence(id, description, null, localFile);
        evidence.setByteFile(localFile);
        List<Evidence> newEvidenceList = new ArrayList<Evidence>();
        newEvidenceList.add(evidence);

        IPersistenceService db = mock(IPersistenceService.class);
        FileSerializer serializer = mock(FileSerializer.class);
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db, serializer);

        when(db.executeQuery(select, caseNumber)).thenReturn(evidenceList);
        
        evidenceRepo.updateEvidence(newEvidenceList, caseNumber);

        verify(db).executeQuery(select, caseNumber);
        verify(db).executeUpdate(insert, evidence.getServerFileLocation(), 
                    evidence.getDescription(), caseNumber);
        verify(db).executeUpdate(delete, "1");
        verify(serializer).write(serverFile, evidence.getByteFile());
    }

    public void testUpdateEvidence_NoOldEvidence() throws SQLException, IOException, RowToModelParseException{
        int id = 2;
        String caseNumber = "1";
        String description = "test evidence";
        File localFile = new File("src/test/resources/file-test.txt");
        File serverFile = new File("data/evidence/1/test_evidence.txt");

        String select = "SELECT id, description, fileLocation FROM evidence WHERE caseNumber=?;";
        String insert = "INSERT INTO evidence VALUES(NULL, ?, ?, ?);";
        
        Evidence evidence = new Evidence(id, description, null, localFile);
        evidence.setByteFile(localFile);
        List<Evidence> newEvidenceList = new ArrayList<Evidence>();
        newEvidenceList.add(evidence);

        IPersistenceService db = mock(IPersistenceService.class);
        FileSerializer serializer = mock(FileSerializer.class);
        IEvidenceRepository evidenceRepo = new EvidenceRepository(db, serializer);

        evidenceRepo.updateEvidence(newEvidenceList, caseNumber);

        verify(db).executeQuery(select, caseNumber);
        verify(db).executeUpdate(insert, evidence.getServerFileLocation(), 
                    evidence.getDescription(), caseNumber);
        verify(serializer).write(serverFile, evidence.getByteFile());
    }
}
