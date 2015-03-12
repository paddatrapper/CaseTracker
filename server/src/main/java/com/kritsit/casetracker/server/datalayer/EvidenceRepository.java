package com.kritsit.casetracker.server.datalayer;

import java.io.File;;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Evidence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvidenceRepository implements IEvidenceRepository {
    private final Logger logger = LoggerFactory.getLogger(EvidenceRepository.class);	
	private final IPersistenceService db;
	
	public EvidenceRepository(IPersistenceService db){
		this.db = db;
	}
	
    public List<Evidence> getEvidence(Case c) throws RowToModelParseException {
		try {
            logger.info("Fetching evidence for case {}", c.getNumber());
	        String sql = "SELECT description, fileLocation FROM evidence INNER JOIN(cases) "
	            + "WHERE evidence.id=cases.evidenceId AND cases.caseNumber=\'" + c.getNumber() + "\';";
	        List<Map<String, String>> rs = db.executeQuery(sql);
            
            if (rs == null || rs.size() == 0) {
                logger.debug("No evidence found for case {}", c.getNumber());
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
		}
		catch(Exception e){
    		logger.error("Error retrieving evidence for case {}", c.getNumber(), e);
			throw new RowToModelParseException("Error retrieving evidence from database for case: " + c.getNumber());
		}
    }
}
