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
        complainant.put("id", "0202215647392");
        complainant.put("firstName", "John");
        complainant.put("lastName", "Smith");
        complainant.put("putress", "1 Long Street, Cape Town");
        complainant.put("emailAddress", "test@test.com");
        complainantList.add(complainant);

        defendantList = new ArrayList<>();
        Map<String, String> defendant = new HashMap<>();
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
        String sql = "SELECT id, firstName, lastName, address, telephoneNumber, emailAddress FROM complainants INNER JOIN(cases) WHERE complainants.indexId=cases.complainantId AND cases.caseNumber=\'" + caseNumber + "\';";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql)).thenReturn(complainantList);
        IPersonRepository personRepo = new PersonRepository(db);

        Person complainant = personRepo.getComplainant(caseNumber);

        assertTrue(complainant != null);
        verify(db).executeQuery(sql);
    }

    public void testGetDefendant() throws SQLException, RowToModelParseException {
        String caseNumber = "1";
        String sql = "SELECT id, firstName, lastName, address, telephoneNumber, emailAddress, secondOffence FROM defendants INNER JOIN(cases) WHERE defendants.indexId=cases.defendantId AND cases.caseNumber=\'" + caseNumber + "\';";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql)).thenReturn(defendantList);
        IPersonRepository personRepo = new PersonRepository(db);

        Defendant defendant = personRepo.getDefendant(caseNumber);

        assertTrue(defendant != null);
        verify(db).executeQuery(sql);
    }
    
    public void testInsertDefendant() throws SQLException, RowToModelParseException{
        String id = "9802245849032";
        Defendant defendant = new Defendant(id, "Bob", "Dylan", "1 address road", "0212221233", "test@testing.co.za", false);
        
        int isSecondOffence = (defendant.isSecondOffence()) ? 1 : 0;
        String sql="INSERT INTO defendants VALUES ('"
            +defendant.getId()+"', '"
            +defendant.getFirstName()+"', '"
            +defendant.getLastName()+"', '"
            +defendant.getAddress()+"', '"
            +defendant.getTelephoneNumber()+"', '"
            +defendant.getEmailAddress()+"', '"
            +isSecondOffence+"');";
        
        IPersistenceService db = mock(IPersistenceService.class);
        IPersonRepository personRepo = new PersonRepository(db);
        personRepo.insertDefendant(defendant);
        verify(db).executeUpdate(sql);
    }
    
    public void testInsertComplainant() throws SQLException, RowToModelParseException{
        String id = "9802245849032";
        Person complainant = new Person(id, "Bob", "Dylan", "1 address road", "0212221233", "test@testing.co.za");

        String sql="INSERT INTO complainants VALUES ('"
            +complainant.getId()+"', '"
            +complainant.getFirstName()+"', '"
            +complainant.getLastName()+"', '"
            +complainant.getAddress()+"', '"
            +complainant.getTelephoneNumber()+"', '"
            +complainant.getEmailAddress()+"');";
        
        IPersistenceService db = mock(IPersistenceService.class);
        IPersonRepository personRepo = new PersonRepository(db);
        personRepo.insertComplainant(complainant);
        verify(db).executeUpdate(sql);
    }
}
