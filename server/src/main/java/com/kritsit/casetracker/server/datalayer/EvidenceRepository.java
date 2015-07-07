package com.kritsit.casetracker.server.datalayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Evidence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class EvidenceRepository implements IEvidenceRepository {
    private final Logger logger = LoggerFactory.getLogger(EvidenceRepository.class);
    private final IPersistenceService db;

    public EvidenceRepository(IPersistenceService db){
        this.db = db;
    }

    public List<Evidence> getEvidence(String caseNumber) throws RowToModelParseException {
        try {
            logger.info("Fetching evidence for case {}", caseNumber);
            String sql = "SELECT description, fileLocation FROM evidence "
                + "WHERE caseNumber=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, caseNumber);

            if (rs == null || rs.isEmpty()) {
                logger.debug("No evidence found for case {}", caseNumber);
                return null;
            }

            List<Evidence> evidenceList = new ArrayList<>();

            for (Map<String, String> line : rs) {
                String description = line.get("description");
                File serverFile = new File(line.get("fileLocation"));
                Evidence evidence = new Evidence(description, serverFile);
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
            String sql = "INSERT INTO evidence VALUES(NULL, ?, ?, ?);";
            db.executeUpdate(sql, evidence.getServerFileLocation(),
                    evidence.getDescription(), caseNumber);
        } catch (SQLException e) {
            logger.error("Unable to insert {}", evidence.toString(), e);
            throw new RowToModelParseException("Unable to insert " +
                    evidence.toString(), e);
        }
    }
}
