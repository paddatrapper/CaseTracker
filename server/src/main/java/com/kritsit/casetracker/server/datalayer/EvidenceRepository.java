package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Evidence;
import com.kritsit.casetracker.shared.domain.FileSerializer;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EvidenceRepository implements IEvidenceRepository {
    private final Logger logger = LoggerFactory.getLogger(EvidenceRepository.class);
    private final IPersistenceService db;
    private final FileSerializer serializer;

    public EvidenceRepository(IPersistenceService db, FileSerializer serializer){
        this.db = db;
        this.serializer = serializer;
    }

    public List<Evidence> getEvidence(String caseNumber) throws RowToModelParseException {
        try {
            logger.info("Fetching evidence for case {}", caseNumber);
            String sql = "SELECT id, description, fileLocation FROM evidence "
                + "WHERE caseNumber=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, caseNumber);

            if (rs == null || rs.isEmpty()) {
                logger.debug("No evidence found for case {}", caseNumber);
                return null;
            }

            List<Evidence> evidenceList = new ArrayList<>();

            for (Map<String, String> line : rs) {
                int id = Integer.parseInt(line.get("id"));
                String description = line.get("description");
                File serverFile = new File(line.get("fileLocation"));
                Evidence evidence = new Evidence(id, description, serverFile);
                evidenceList.add(evidence);
            }
            return evidenceList;
        } catch (SQLException | NullPointerException e) {
            logger.error("Error retrieving evidence for case {}", caseNumber, e);
            throw new RowToModelParseException("Error retrieving evidence " +
                    "from database for case: " + caseNumber, e);
        }
    }

    public void insertEvidence(Evidence evidence, String caseNumber) throws RowToModelParseException {
        try {
            logger.info("Inserting {}", evidence.toString());
            String sql = "INSERT INTO evidence VALUES(NULL, ?, ?, ?);";
            evidence = saveEvidence(evidence, caseNumber);
            db.executeUpdate(sql, evidence.getServerFileLocation(),
                    evidence.getDescription(), caseNumber);
        } catch (IOException | SQLException e) {
            logger.error("Unable to insert {}", evidence.toString(), e);
            throw new RowToModelParseException("Unable to insert " +
                    evidence.toString(), e);
        }
    }

    public void updateEvidence(List<Evidence> evidence, String caseNumber) throws RowToModelParseException {
        List<Evidence> oldEvidence = getEvidence(caseNumber);
        RowToModelParseException exception = null;
        if (oldEvidence != null) {
            for (Evidence e : oldEvidence) {
                try {
                    if (!evidence.contains(e)) {
                        deleteEvidence(e.getId());
                        removeEvidence(e);
                    }
                } catch (RowToModelParseException ex) {
                    logger.error("Error deleting evidence {} from the database", e, ex);
                    exception = ex;
                } catch (IOException ex) {
                    logger.error("Error removing evidence {} from the file system", e, ex);
                    exception = new RowToModelParseException("Unable to delete evidence", ex);
                }
            }
        }
        for (Evidence e : evidence) {
            try {
                if (oldEvidence == null || !oldEvidence.contains(e)) {
                    insertEvidence(e, caseNumber);
                }
            } catch (RowToModelParseException ex) {
                logger.error("Error saving evidence {}", e.getDescription(), ex);
                if (exception != null) {
                    throw exception;
                }
                exception = ex;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    private void deleteEvidence(int indexId) throws RowToModelParseException {
        try {
            logger.info("Deleting evidence {}", indexId);
            String sql = "DELETE FROM evidence WHERE id=?;";
            db.executeUpdate(sql, String.valueOf(indexId));
        } catch (SQLException e) {
            logger.error("Error deleting evidence {}", indexId, e);
            throw new RowToModelParseException("Error deleting evidence", e);
        }
    }

    private Evidence saveEvidence(Evidence evidence, String caseNumber) throws IOException {
        String fileName = evidence.getDescription().replaceAll(" ", "_");
        if (evidence.getLocalFile().getName().contains(".")) {
            String clientFileName = evidence.getLocalFile().getName();
            fileName += clientFileName.substring(clientFileName.lastIndexOf("."));
        }
        fileName = fileName.replaceAll("/", "");
        fileName = fileName.replaceAll(" ", "_");
        String fileLocation = "data/evidence/" + caseNumber + "/" + fileName;
        File f = new File(fileLocation);
        serializer.write(f, evidence.getByteFile());
        evidence.setServerFile(f);
        return evidence;
    }

    private void removeEvidence(Evidence e) throws IOException {
       File f = e.getServerFile();
       f.delete();
    }
}
