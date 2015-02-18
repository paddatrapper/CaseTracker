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
}
