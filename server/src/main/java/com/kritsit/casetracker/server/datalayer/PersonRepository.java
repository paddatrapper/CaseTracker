package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Defendant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonRepository implements IPersonRepository {
    private final Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    private final IPersistenceService db;

    public PersonRepository(IPersistenceService db){
        this.db = db;
    }

    public Person getComplainant(String caseNumber) throws RowToModelParseException {
        try {
            logger.info("Fetching complainant for case {}", caseNumber);
            String sql = "SELECT indexID, id, firstName, lastName, address, telephoneNumber, " +
                "emailAddress FROM complainants INNER JOIN(cases) " +
                "WHERE complainants.indexId=cases.complainantId AND cases.caseNumber=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, caseNumber);

            if(rs == null || rs.isEmpty()) {
                logger.debug("No complainants found for case {}", caseNumber);
                return null;
            }

            int indexId = Integer.parseInt(rs.get(0).get("indexID"));
            String id = rs.get(0).get("id");
            String firstName = rs.get(0).get("firstName");
            String lastName = rs.get(0).get("lastName");
            String address = rs.get(0).get("address");
            String telephoneNumber = rs.get(0).get("telephoneNumber");
            String emailAddress = rs.get(0).get("emailAddress");

            Person complainant = new Person(indexId, id, firstName, lastName, address,
                    telephoneNumber, emailAddress);
            return complainant;
        } catch (SQLException e) {
            logger.error("Error retrieving complainant for case {}", caseNumber, e);
            throw new RowToModelParseException("Error retrieving complainant from " +
                    "database for case number: " + caseNumber, e);
        }
    }

    public Defendant getDefendant(String caseNumber) throws RowToModelParseException {
        try {
            logger.info("Fetching defendant for case {}", caseNumber);
            String sql = "SELECT indexID, id, firstName, lastName, address, telephoneNumber, " +
                "emailAddress, secondOffence FROM defendants INNER JOIN(cases) " +
                "WHERE defendants.indexId=cases.defendantId AND cases.caseNumber=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, caseNumber);

            if(rs == null || rs.isEmpty()) {
                logger.debug("No defendants found for case {}", caseNumber);
                return null;
            }

            int indexId = Integer.parseInt(rs.get(0).get("indexID"));
            String id = rs.get(0).get("id");
            String firstName = rs.get(0).get("firstName");
            String lastName = rs.get(0).get("lastName");
            String address = rs.get(0).get("address");
            String telephoneNumber = rs.get(0).get("telephoneNumber");
            String emailAddress = rs.get(0).get("emailAddress");
            boolean isSecondOffence = "1".equals(rs.get(0).get("secondOffence"));

            Defendant defendant = new Defendant(indexId, id, firstName, lastName, address,
                    telephoneNumber, emailAddress, isSecondOffence);
            return defendant;
        } catch (SQLException e) {
            logger.error("Error retrieving defendant for case {}", caseNumber, e);
            throw new RowToModelParseException("Error retrieving defendant from " +
                    "database for case number: " + caseNumber, e);
        }
    }

    public int insertDefendant(Defendant defendant) throws RowToModelParseException{
        try{
            Map<String, String[]>  queryMap = getInsertQuery(defendant, "defendants");
            String query = queryMap.get("query")[0];
            String[] args = queryMap.get("arguments");
            List<Map<String, String>> rs = db.executeQuery(query, args); 

            if(rs == null || rs.isEmpty()) {
                logger.info("Inserting defendant {}", defendant.getName());
                String isSecondOffence = (defendant.isSecondOffence()) ? "1" : "0";
                String sql = "INSERT INTO defendants VALUES (NULL, ?, ?, ?, ?, ?, " +
                    "?, ?);";
                db.executeUpdate(sql, defendant.getId(), defendant.getFirstName(),
                        defendant.getLastName(), defendant.getAddress(),
                        defendant.getTelephoneNumber(), defendant.getEmailAddress(),
                        isSecondOffence);
                queryMap = getInsertQuery(defendant, "defendants");
                query = queryMap.get("query")[0];
                args = queryMap.get("arguments");
                rs = db.executeQuery(query, args); 

                if(rs == null || rs.isEmpty()) {
                    logger.warn("No defendant added to database");
                    throw new RowToModelParseException("Error inserting " + defendant.toString(), null);
                }
            } else {
                String sql = "UPDATE defendants SET secondOffence=1 WHERE indexID=?;";
                db.executeUpdate(sql, rs.get(0).get("indexID"));
            }
            return Integer.parseInt(rs.get(0).get("indexID"));
        } catch (SQLException | NumberFormatException e) {
            logger.error("Error inserting defendant into the database", e);
            throw new RowToModelParseException("Error inserting values to database", e);
        }
    }

    public int insertComplainant(Person complainant) throws RowToModelParseException{
        try{
            Map<String, String[]>  queryMap = getInsertQuery(complainant, "complainants");
            String query = queryMap.get("query")[0];
            String[] args = queryMap.get("arguments");
            List<Map<String, String>> rs = db.executeQuery(query, args); 

            if(rs == null || rs.isEmpty()) {
                logger.info("Inserting complainant {}", complainant.getName());
                String sql = "INSERT INTO complainants VALUES (NULL, ?, ?, ?, " +
                    "?, ?, ?);";
                db.executeUpdate(sql, complainant.getId(), complainant.getFirstName(),
                        complainant.getLastName(), complainant.getAddress(),
                        complainant.getTelephoneNumber(), complainant.getEmailAddress());
                queryMap = getInsertQuery(complainant, "complainants");
                query = queryMap.get("query")[0];
                args = queryMap.get("arguments");
                rs = db.executeQuery(query, args); 

                if(rs == null || rs.isEmpty()) {
                    logger.warn("No complainant added to database");
                    throw new RowToModelParseException("Error inserting " + complainant.toString(), null);
                }
            }
            return Integer.parseInt(rs.get(0).get("indexID"));
        } catch (SQLException | NumberFormatException e) {
            logger.error("Error inserting complainant into the database", e);
            throw new RowToModelParseException("Error inserting values to database", e);
        }
    }

    private Map<String, String[]> getInsertQuery(Person person, String tableName) {
        StringBuilder insertStringBuilder = new StringBuilder();
        ArrayList<String> args = new ArrayList<>();
        insertStringBuilder.append("SELECT indexID FROM ");
        insertStringBuilder.append(tableName);
        insertStringBuilder.append(" WHERE lastName=?");
        args.add(person.getLastName());
        if (person.getId() != null && !person.getId().isEmpty()) {
            insertStringBuilder.append(" AND id=?");
            args.add(person.getId());
        }
        if (person.getFirstName() != null && !person.getFirstName().isEmpty()) {
            insertStringBuilder.append(" AND firstName=?");
            args.add(person.getFirstName());
        }
        if (person.getAddress() != null && !person.getAddress().isEmpty()) {
            insertStringBuilder.append(" AND address=?");
            args.add(person.getAddress());
        }
        if (person.getTelephoneNumber() != null && !person.getTelephoneNumber().isEmpty()) {
            insertStringBuilder.append(" AND telephoneNumber=?");
            args.add(person.getTelephoneNumber());
        }
        if (person.getEmailAddress() != null && !person.getEmailAddress().isEmpty()) {
            insertStringBuilder.append(" AND emailAddress=?");
            args.add(person.getEmailAddress());
        }
        insertStringBuilder.append(";");
        Map<String, String[]> result = new HashMap<>();
        String[] query = {insertStringBuilder.toString()};
        result.put("query", query);
        result.put("arguments", args.toArray(new String[0]));
        return result;
    }
}
