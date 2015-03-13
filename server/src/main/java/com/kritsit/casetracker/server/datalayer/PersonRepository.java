package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Defendant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonRepository implements IPersonRepository {
    private final Logger logger = LoggerFactory.getLogger(PersonRepository.class);	
	private final IPersistenceService db;
	
	public PersonRepository(IPersistenceService db){
		this.db = db;
	}

    public Person getComplainant(String caseNumber) throws RowToModelParseException {
        try {
            logger.info("Fetching complainant for case {}", caseNumber);
            String sql = "SELECT id, firstName, lastName, address, telephoneNumber, emailAddress FROM complainants INNER JOIN(cases) WHERE complainants.indexId=cases.complainantId AND cases.caseNumber=\'" + caseNumber + "\';";
            List<Map<String, String>> rs = db.executeQuery(sql);

            if(rs == null || rs.size() == 0) {
                logger.debug("No complainants found for case {}", caseNumber);
                return null;
            }
            
            String id = rs.get(0).get("id");
            String firstName = rs.get(0).get("firstName");
            String lastName = rs.get(0).get("lastName");
            String address = rs.get(0).get("address");
            String telephoneNumber = rs.get(0).get("telephoneNumber");
            String emailAddress = rs.get(0).get("emailAddress");
            
            Person complainant = new Person(id, firstName, lastName, address, telephoneNumber, emailAddress);
            return complainant;
        } catch(Exception e){
            logger.error("Error retrieving complainant for case {}", caseNumber, e);
            throw new RowToModelParseException("Error retrieving complainant from database for case number: " + caseNumber);
        }
    }

    public Defendant getDefendant(String caseNumber) throws RowToModelParseException {
        try {
            logger.info("Fetching defendant for case {}", caseNumber);
            String sql = "SELECT id, firstName, lastName, address, telephoneNumber, emailAddress, secondOffence FROM defendants INNER JOIN(cases) WHERE defendants.indexId=cases.defendantId AND cases.caseNumber=\'" + caseNumber + "\';";
            List<Map<String, String>> rs = db.executeQuery(sql);

            if(rs == null || rs.size() == 0) {
                logger.debug("No defendants found for case {}", caseNumber);
                return null;
            }
            
            String id = rs.get(0).get("id");
            String firstName = rs.get(0).get("firstName");
            String lastName = rs.get(0).get("lastName");
            String address = rs.get(0).get("address");
            String telephoneNumber = rs.get(0).get("telephoneNumber");
            String emailAddress = rs.get(0).get("emailAddress");
            boolean isSecondOffence = Boolean.parseBoolean(rs.get(0).get("secondOffence"));
            
            Defendant defendant = new Defendant(id, firstName, lastName, address, telephoneNumber, emailAddress, isSecondOffence);
            return defendant;
        } catch(Exception e){
            logger.error("Error retrieving defendant for case {}", caseNumber, e);
            throw new RowToModelParseException("Error retrieving defendant from database for case number: " + caseNumber);
        }
    }
}
