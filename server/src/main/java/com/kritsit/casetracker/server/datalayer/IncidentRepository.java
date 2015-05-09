package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Incident;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncidentRepository implements IIncidentRepository {
    private final Logger logger = LoggerFactory.getLogger(IncidentRepository.class);	
	private final IPersistenceService db;
	
	public IncidentRepository(IPersistenceService db){
		this.db = db;
	}

    public Incident getIncident(String caseNumber) throws RowToModelParseException {
        try {
            logger.info("Fetching incident for case {}", caseNumber);
            String sql = "SELECT incidents.* FROM incidents INNER JOIN(cases) WHERE incidents.id=cases.incidentId AND cases.caseNumber='" + caseNumber + "';";
            List<Map<String, String>> rs = db.executeQuery(sql);

            if(rs == null || rs.size() == 0) {
                logger.debug("No incident found for case {}", caseNumber);
                return null;
            }
            String region = rs.get(0).get("region");
            LocalDate date = LocalDate.parse(rs.get(0).get("incidentDate"));
            LocalDate followUpDate = LocalDate.parse(rs.get(0).get("followUpDate"));
            boolean isFollowedUp = Boolean.parseBoolean(rs.get(0).get("followedUp"));
            if (rs.get(0).get("address") == null) {
                float longitude = Float.parseFloat(rs.get(0).get("longitude"));
                float latitude = Float.parseFloat(rs.get(0).get("latitude"));
                Incident i = new Incident(longitude, latitude, region, date, followUpDate, isFollowedUp);
                return i;
            } else {
                String address = rs.get(0).get("address");
                Incident i = new Incident(address, region, date, followUpDate, isFollowedUp);
                return i;
            }
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            logger.error("Error retrieving incident for {}", caseNumber, e);
            throw new RowToModelParseException("Error retrieving incident from database for case number: " + caseNumber);
        }
    }
}
