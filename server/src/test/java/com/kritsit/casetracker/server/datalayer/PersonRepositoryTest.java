package com.kritsit.casetracker.server.datalayer;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Staff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonRepositoryTest extends TestCase {
    private IPersonRepository personRepo;
    private List<Map<String, String>> complainantList;
    private List<Map<String, String>> defendantList;

    public PersonRepositoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(PersonRepositoryTest.class);
    }

    public void setUp() {
        complainantList = new ArrayList<>();
        Map<String, String> complainant = new HashMap<>();
        complainant.put("indexID", "1");
        complainant.put("id", "0202215647392");
        complainant.put("firstName", "John");
        complainant.put("lastName", "Smith");
        complainant.put("putress", "1 Long Street, Cape Town");
        complainant.put("emailAddress", "test@test.com");
        complainantList.add(complainant);

        defendantList = new ArrayList<>();
        Map<String, String> defendant = new HashMap<>();
        defendant.put("indexID", "1");
        defendant.put("id", "0202215647392");
        defendant.put("firstName", "John");
        defendant.put("lastName", "Smith");
        defendant.put("putress", "1 Long Street, Cape Town");
        defendant.put("emailAddress", "test@test.com");
        defendant.put("secondOffence", "0");
        defendantList.add(defendant);
    }

    public void testGetComplainant() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT indexID, id, firstName, lastName, address, telephoneNumber, " +
            "emailAddress FROM complainants INNER JOIN(cases) " +
            "WHERE complainants.indexId=cases.complainantId AND cases.caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql, caseNumber)).thenReturn(complainantList);
        IPersonRepository personRepo = new PersonRepository(db);

        Person complainant = personRepo.getComplainant(caseNumber);

        assertTrue(complainant != null);
        verify(db).executeQuery(sql, caseNumber);
    }

    public void testGetDefendant() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT indexID, id, firstName, lastName, address, telephoneNumber, " +
            "emailAddress, secondOffence FROM defendants INNER JOIN(cases) " +
            "WHERE defendants.indexId=cases.defendantId AND cases.caseNumber=?;";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql, caseNumber)).thenReturn(defendantList);
        IPersonRepository personRepo = new PersonRepository(db);

        Defendant defendant = personRepo.getDefendant(caseNumber);

        assertTrue(defendant != null);
        verify(db).executeQuery(sql, caseNumber);
    }

    public void testInsertDefendant_New() throws SQLException, RowToModelParseException{
        int indexId = 1;
        String id = "9802245849032";
        Defendant defendant = new Defendant(indexId, id, "Bob", "Dylan", "1 address road",
                "0212221233", "test@testing.co.za", false);
        String isSecondOffence = (defendant.isSecondOffence()) ? "1" : "0";
        String sql = "INSERT INTO defendants VALUES (NULL, ?, ?, ?, ?, ?, " +
            "?, ?);";

        IPersistenceService db = mock(IPersistenceService.class);
        IPersonRepository personRepo = new PersonRepository(db);
        String query = "SELECT indexID FROM defendants WHERE lastName=? AND " +
            "id=? AND firstName=? AND address=? AND telephoneNumber=? AND " +
            "emailAddress=?;";
        List<Map<String, String>> idList = new ArrayList<>();
        Map<String, String> idMap = new HashMap<>();
        idMap.put("indexID", String.valueOf(indexId));
        idList.add(idMap);

        when(db.executeQuery(query, defendant.getLastName(), defendant.getId(),
                    defendant.getFirstName(), defendant.getAddress(),
                    defendant.getTelephoneNumber(), defendant.getEmailAddress()))
            .thenReturn(null)
            .thenReturn(idList);

        int resultIndexId = personRepo.insertDefendant(defendant);

        assertTrue(indexId == resultIndexId);
        verify(db).executeUpdate(sql, defendant.getId(), defendant.getFirstName(),
                defendant.getLastName(), defendant.getAddress(),
                defendant.getTelephoneNumber(), defendant.getEmailAddress(),
                isSecondOffence);
        verify(db, times(2)).executeQuery(query, defendant.getLastName(),
                defendant.getId(), defendant.getFirstName(), defendant.getAddress(),
                defendant.getTelephoneNumber(), defendant.getEmailAddress());
    }

    public void testInsertDefendant_Existing() throws SQLException, RowToModelParseException{
        int indexId = 1;
        String id = "9802245849032";
        Defendant defendant = new Defendant(indexId, id, "Bob", "Dylan", "1 address road",
                "0212221233", "test@testing.co.za", false);
        String isSecondOffence = (defendant.isSecondOffence()) ? "1" : "0";
        String sql = "UPDATE defendants SET secondOffence=1 WHERE indexID=?;";

        IPersistenceService db = mock(IPersistenceService.class);
        IPersonRepository personRepo = new PersonRepository(db);
        String query = "SELECT indexID FROM defendants WHERE lastName=? AND " +
            "id=? AND firstName=? AND address=? AND telephoneNumber=? AND " +
            "emailAddress=?;";
        List<Map<String, String>> idList = new ArrayList<>();
        Map<String, String> idMap = new HashMap<>();
        idMap.put("indexID", String.valueOf(indexId));
        idList.add(idMap);

        when(db.executeQuery(query, defendant.getLastName(), defendant.getId(),
                    defendant.getFirstName(), defendant.getAddress(),
                    defendant.getTelephoneNumber(), defendant.getEmailAddress()))
            .thenReturn(idList);

        int resultIndexId = personRepo.insertDefendant(defendant);

        assertTrue(indexId == resultIndexId);
        verify(db).executeUpdate(sql, String.valueOf(indexId));
        verify(db).executeQuery(query, defendant.getLastName(),
                defendant.getId(), defendant.getFirstName(), defendant.getAddress(),
                defendant.getTelephoneNumber(), defendant.getEmailAddress());
    }

    public void testInsertComplainant_New() throws SQLException, RowToModelParseException{
        int indexId = 1;
        String id = "9802245849032";
        Person complainant = new Person(indexId, id, "Bob", "Dylan", "1 address road",
                "0212221233", "test@testing.co.za");
        String sql = "INSERT INTO complainants VALUES (NULL, ?, ?, ?, ?, ?, " +
            "?);";

        IPersistenceService db = mock(IPersistenceService.class);
        IPersonRepository personRepo = new PersonRepository(db);
        String query = "SELECT indexID FROM complainants WHERE lastName=? AND " +
            "id=? AND firstName=? AND address=? AND telephoneNumber=? AND " +
            "emailAddress=?;";
        List<Map<String, String>> idList = new ArrayList<>();
        Map<String, String> idMap = new HashMap<>();
        idMap.put("indexID", String.valueOf(indexId));
        idList.add(idMap);

        when(db.executeQuery(query, complainant.getLastName(), complainant.getId(),
                complainant.getFirstName(), complainant.getAddress(),
                complainant.getTelephoneNumber(), complainant.getEmailAddress()))
            .thenReturn(null)
            .thenReturn(idList);

        int returnIndexId = personRepo.insertComplainant(complainant);

        assertTrue(returnIndexId == indexId);
        verify(db).executeUpdate(sql, complainant.getId(), complainant.getFirstName(),
                complainant.getLastName(), complainant.getAddress(),
                complainant.getTelephoneNumber(), complainant.getEmailAddress());
        verify(db, times(2)).executeQuery(query, complainant.getLastName(), complainant.getId(),
                complainant.getFirstName(), complainant.getAddress(),
                complainant.getTelephoneNumber(), complainant.getEmailAddress());
    }

    public void testInsertComplainant_Existing() throws SQLException, RowToModelParseException{
        int indexId = 1;
        String id = "9802245849032";
        Person complainant = new Person(indexId, id, "Bob", "Dylan", "1 address road",
                "0212221233", "test@testing.co.za");
        IPersistenceService db = mock(IPersistenceService.class);
        IPersonRepository personRepo = new PersonRepository(db);
        String query = "SELECT indexID FROM complainants WHERE lastName=? AND " +
            "id=? AND firstName=? AND address=? AND telephoneNumber=? AND " +
            "emailAddress=?;";
        List<Map<String, String>> idList = new ArrayList<>();
        Map<String, String> idMap = new HashMap<>();
        idMap.put("indexID", String.valueOf(indexId));
        idList.add(idMap);

        when(db.executeQuery(query, complainant.getLastName(), complainant.getId(),
                complainant.getFirstName(), complainant.getAddress(),
                complainant.getTelephoneNumber(), complainant.getEmailAddress()))
            .thenReturn(idList);

        int returnIndexId = personRepo.insertComplainant(complainant);

        assertTrue(returnIndexId == indexId);
        verify(db).executeQuery(query, complainant.getLastName(), complainant.getId(),
                complainant.getFirstName(), complainant.getAddress(),
                complainant.getTelephoneNumber(), complainant.getEmailAddress());
    }
}
