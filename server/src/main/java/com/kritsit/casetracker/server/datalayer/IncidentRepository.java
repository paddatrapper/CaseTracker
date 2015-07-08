package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Incident;
import java.sql.SQLException;

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
            String sql = "SELECT incidents.* FROM incidents INNER JOIN(cases) " +
                "WHERE incidents.id=cases.incidentId AND cases.caseNumber=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, caseNumber);

            if(rs == null || rs.isEmpty()) {
                logger.debug("No incident found for case {}", caseNumber);
                return null;
            }

            int indexId = Integer.parseInt(rs.get(0).get("id"));
            String region = rs.get(0).get("region");
            LocalDate date = LocalDate.parse(rs.get(0).get("incidentDate"));
            LocalDate followUpDate = LocalDate.parse(rs.get(0).get("followUpDate"));
            boolean isFollowedUp = Boolean.parseBoolean(rs.get(0).get("followedUp"));

            if (rs.get(0).get("address") == null) {
                float longitude = Float.parseFloat(rs.get(0).get("longitude"));
                float latitude = Float.parseFloat(rs.get(0).get("latitude"));
                Incident i = new Incident(indexId, longitude, latitude, region, date,
                        followUpDate, isFollowedUp);
                return i;
            } else {
                String address = rs.get(0).get("address");
                Incident i = new Incident(indexId, address, region, date, followUpDate,
                        isFollowedUp);
                return i;
            }
        } catch (SQLException e){
            logger.error("Error retrieving incident for {}", caseNumber, e);
            throw new RowToModelParseException("Error retrieving incident from " +
                    "database for case number: " + caseNumber, e);
        }
    }

    public int insertIncident(Incident incident) throws RowToModelParseException {
        try {
            logger.info("Adding {}", incident.toString());
            String isFollowedUp = incident.isFollowedUp() ? "1" : "0";
            List<Map<String, String>> rs = null;
            if (incident.getAddress() == null || incident.getAddress().isEmpty()) {
                String update = "INSERT INTO incidents VALUES (NULL, ?, ?, NULL, ?, ?, ?, ?);";
                db.executeUpdate(update, String.valueOf(incident.getLongitude()),
                        String.valueOf(incident.getLatitude()), incident.getRegion(),
                        incident.getDate().toString(), incident.getFollowUpDate().toString(),
                        isFollowedUp);
                String query = "SELECT id FROM incidents WHERE longitude=? AND " +
                    "latitude=? AND region=? AND incidentDate=? AND followUpDate=?;";
                rs = db.executeQuery(query, String.valueOf(incident.getLongitude()), 
                        String.valueOf(incident.getLatitude()), incident.getRegion(), 
                        incident.getDate().toString(), incident.getFollowUpDate().toString());
            } else {
                String sql = "INSERT INTO incidents VALUES (NULL, NULL, NULL, ?, ?, ?, ?, ?);";
                db.executeUpdate(sql, incident.getAddress(), incident.getRegion(),
                        incident.getDate().toString(), incident.getFollowUpDate().toString(),
                        isFollowedUp);
                String query = "SELECT id FROM incidents WHERE address=? AND " +
                    "region=? AND incidentDate=? AND followUpDate=?;";
                rs = db.executeQuery(query, incident.getAddress(), incident.getRegion(), 
                        incident.getDate().toString(), incident.getFollowUpDate().toString());
            }
            if (rs == null || rs.isEmpty()) {
                logger.warn("No incident added");
                throw new RowToModelParseException("Error inserting " + incident.toString(), null);
            }
            return Integer.parseInt(rs.get(0).get("id"));
        } catch (SQLException e) {
            logger.error("Error inserting {}", incident.toString());
            throw new RowToModelParseException("Error inserting " + incident.toString(), e);
        }
    }
}
