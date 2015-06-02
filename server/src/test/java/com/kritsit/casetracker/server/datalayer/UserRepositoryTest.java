package com.kritsit.casetracker.server.datalayer;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.shared.domain.model.Staff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryTest extends TestCase {

    public UserRepositoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(UserRepositoryTest.class);
    }

	public void testGetUserDetails() throws Exception {
        String username = "inspector";
        String sql = "SELECT firstName, lastName, department, position, permissions FROM staff WHERE username=\'" + username + "\';";

		IPersistenceService db = mock(IPersistenceService.class);
		UserRepository repo = new UserRepository(db);
        Map<String, String> details = new HashMap<>();
        details.put("firstName", "Inspector");
        details.put("lastName", "Inspector");
        details.put("department", "Inspectorate");
        details.put("position", "inspector");
        details.put("permissions", "1");
        List<Map<String, String>> result = new ArrayList<>();
        result.add(details);
	    when(db.executeQuery(sql)).thenReturn(result);	

        Staff response = repo.getUserDetails(username);

        assertTrue(response != null);
        verify(db).executeQuery(sql);
    }

	public void testGetSalt() throws Exception{
		String username = "inspector";
        String sql = "SELECT salt FROM staff WHERE username=\'" + username + "\';";
		long expectedSalt = -5922475058261058398L;
		
		IPersistenceService db = mock(IPersistenceService.class);
		UserRepository repo = new UserRepository(db);
        Map<String, String> details = new HashMap<>();
        details.put("salt", expectedSalt + "");
        List<Map<String, String>> result = new ArrayList<>();
        result.add(details);
	    when(db.executeQuery(sql)).thenReturn(result);	

		long actualSalt = repo.getSalt(username);
		
		assertTrue(expectedSalt == actualSalt);
	    verify(db).executeQuery(sql);
	}
	
    public void testGetPasswordSaltedHash() throws Exception {
        String username = "inspector";
        String sql = "SELECT passwordHash FROM staff WHERE username=\'" + username + "\';";
        long expectedSalt = -5922475058261058398L;
        long expectedPasswordSaltedHash = "inspector".hashCode() + expectedSalt;
        
		IPersistenceService db = mock(IPersistenceService.class);
		UserRepository repo = new UserRepository(db);
        Map<String, String> details = new HashMap<>();
        details.put("passwordHash", expectedPasswordSaltedHash + "");
        List<Map<String, String>> result = new ArrayList<>();
        result.add(details);
	    when(db.executeQuery(sql)).thenReturn(result);	

        long passwordSaltedHash = repo.getPasswordSaltedHash(username);
        
        assertTrue(expectedPasswordSaltedHash == passwordSaltedHash);
	    verify(db).executeQuery(sql);
    }

	public void testGetInvestigatingOfficer() throws Exception {
        String caseNumber = "1";
        String username = "inspector";
        String investigatingOfficerSql = "SELECT username FROM staff INNER JOIN(cases) WHERE staff.id=cases.staffID AND cases.caseNumber=\'" + caseNumber + "\';";
        String detailsSql = "SELECT firstName, lastName, department, position, permissions FROM staff WHERE username=\'" + username + "\';";

		IPersistenceService db = mock(IPersistenceService.class);
		UserRepository repo = new UserRepository(db);
        Map<String, String> details = new HashMap<>();
        details.put("firstName", "Inspector");
        details.put("lastName", "Inspector");
        details.put("department", "Inspectorate");
        details.put("position", "inspector");
        details.put("permissions", "1");
        List<Map<String, String>> detailsList = new ArrayList<>();
        detailsList.add(details);

        Map<String, String> usernameMap = new HashMap<>();
        usernameMap.put("username", username);
        List<Map<String, String>> usernameList = new ArrayList<>();
        usernameList.add(usernameMap);

	    when(db.executeQuery(investigatingOfficerSql)).thenReturn(usernameList);
	    when(db.executeQuery(detailsSql)).thenReturn(detailsList);

        Staff response = repo.getInvestigatingOfficer(caseNumber);

        assertTrue(response != null);
        verify(db).executeQuery(investigatingOfficerSql);
        verify(db).executeQuery(detailsSql);
    }

    public void testGetGetInspectors() throws Exception {
        String username = "inspector";
        String anotherUsername = "AnotherInspector";
        String usernameListSql = "SELECT username FROM staff WHERE permissions=1;";
        String inspectorDetailsSql = "SELECT firstName, lastName, department, position, permissions FROM staff WHERE username=\'" + username + "\';";
        String anotherInspectorDetailsSql = "SELECT firstName, lastName, department, position, permissions FROM staff WHERE username=\'" + anotherUsername + "\';";

        IPersistenceService db = mock(IPersistenceService.class);
		UserRepository repo = new UserRepository(db);
        List<Map<String, String>> inspector = new ArrayList<>();
        Map<String, String> user1 = new HashMap<>();
        user1.put("firstName", "Inspector");
        user1.put("lastName", "Inspector");
        user1.put("department", "Inspectorate");
        user1.put("position", "inspector");
        user1.put("permissions", "1");
        inspector.add(user1);

        List<Map<String, String>> anotherInspector = new ArrayList<>();
        Map<String, String> user2 = new HashMap<>();
        user2.put("firstName", "Another");
        user2.put("lastName", "Inspector");
        user2.put("department", "Inspectorate");
        user2.put("position", "manager");
        user2.put("permissions", "1");
        anotherInspector.add(user2);

        Map<String, String> usernameMap = new HashMap<>();
        usernameMap.put("username", username);
        List<Map<String, String>> usernameList = new ArrayList<>();
        usernameList.add(usernameMap);

        Map<String, String> anotherUsernameMap = new HashMap<>();
        anotherUsernameMap.put("username", anotherUsername);
        usernameList.add(anotherUsernameMap);
        
	    when(db.executeQuery(usernameListSql)).thenReturn(usernameList);
        when(db.executeQuery(inspectorDetailsSql)).thenReturn(inspector);
        when(db.executeQuery(anotherInspectorDetailsSql)).thenReturn(anotherInspector);

        List<Staff> response = repo.getInspectors();

        assertTrue(response != null);
        verify(db).executeQuery(usernameListSql);
        verify(db).executeQuery(inspectorDetailsSql);
        verify(db).executeQuery(anotherInspectorDetailsSql);
    }
}
