package com.kritsit.casetracker.server.datalayer;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.server.domain.model.AuthenticationException;
import com.kritsit.casetracker.shared.domain.model.Staff;
import com.kritsit.casetracker.shared.domain.model.Permission;

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
        String sql = "SELECT firstName, lastName, department, position, " +
            "permissions FROM staff WHERE username=?;";

        IPersistenceService db = mock(IPersistenceService.class);
        IUserRepository repo = new UserRepository(db);
        Map<String, String> details = new HashMap<>();
        details.put("firstName", "Inspector");
        details.put("lastName", "Inspector");
        details.put("department", "Inspectorate");
        details.put("position", "inspector");
        details.put("permissions", "1");
        List<Map<String, String>> result = new ArrayList<>();
        result.add(details);
        when(db.executeQuery(sql, username)).thenReturn(result);    

        Staff response = repo.getUserDetails(username);

        assertTrue(response != null);
        verify(db).executeQuery(sql, username);
    }

    public void testGetSalt() throws Exception{
        String username = "inspector";
        String sql = "SELECT salt FROM staff WHERE username=?;";
        long expectedSalt = -5922475058261058398L;
        
        IPersistenceService db = mock(IPersistenceService.class);
        IUserRepository repo = new UserRepository(db);
        Map<String, String> details = new HashMap<>();
        details.put("salt", expectedSalt + "");
        List<Map<String, String>> result = new ArrayList<>();
        result.add(details);
        when(db.executeQuery(sql, username)).thenReturn(result);    

        long actualSalt = repo.getSalt(username);
        
        assertTrue(expectedSalt == actualSalt);
        verify(db).executeQuery(sql, username);
    }
    
    public void testGetPasswordSaltedHash_Null() throws Exception {
        String username = "inspector";
        String sql = "SELECT passwordHash FROM staff WHERE username=?;";
        
        IPersistenceService db = mock(IPersistenceService.class);
        IUserRepository repo = new UserRepository(db);
        when(db.executeQuery(sql, username)).thenReturn(null);    

        boolean exceptionCalled = false;
        try {
            long passwordSaltedHash = repo.getPasswordSaltedHash(username);
        } catch (AuthenticationException ex) {
            exceptionCalled = true;
        } 
        assertTrue(exceptionCalled);
        verify(db).executeQuery(sql, username);
    }

    public void testGetPasswordSaltedHash() throws Exception {
        String username = "inspector";
        String sql = "SELECT passwordHash FROM staff WHERE username=?;";
        long expectedSalt = -5922475058261058398L;
        long expectedPasswordSaltedHash = "inspector".hashCode() + expectedSalt;
        
        IPersistenceService db = mock(IPersistenceService.class);
        IUserRepository repo = new UserRepository(db);
        Map<String, String> details = new HashMap<>();
        details.put("passwordHash", expectedPasswordSaltedHash + "");
        List<Map<String, String>> result = new ArrayList<>();
        result.add(details);
        when(db.executeQuery(sql, username)).thenReturn(result);    

        long passwordSaltedHash = repo.getPasswordSaltedHash(username);
        
        assertTrue(expectedPasswordSaltedHash == passwordSaltedHash);
        verify(db).executeQuery(sql, username);
    }

    public void testGetInvestigatingOfficer() throws Exception {
        String caseNumber = "1";
        String username = "inspector";
        String investigatingOfficerSql = "SELECT username FROM staff " +
            "INNER JOIN(cases) WHERE staff.username=cases.staffID AND cases.caseNumber=?;";
        String detailsSql = "SELECT firstName, lastName, department, position, " +
            "permissions FROM staff WHERE username=?;";

        IPersistenceService db = mock(IPersistenceService.class);
        IUserRepository repo = new UserRepository(db);
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

        when(db.executeQuery(investigatingOfficerSql, caseNumber)).thenReturn(usernameList);
        when(db.executeQuery(detailsSql, username)).thenReturn(detailsList);

        Staff response = repo.getInvestigatingOfficer(caseNumber);

        assertTrue(response != null);
        verify(db).executeQuery(investigatingOfficerSql, caseNumber);
        verify(db).executeQuery(detailsSql, username);
    }

    public void testGetGetInspectors() throws Exception {
        String username = "inspector";
        String anotherUsername = "AnotherInspector";
        String usernameListSql = "SELECT username FROM staff WHERE permissions=?;";
        String inspectorDetailsSql = "SELECT firstName, lastName, department, " +
            "position, permissions FROM staff WHERE username=?;";
        String anotherInspectorDetailsSql = "SELECT firstName, lastName, " +
            "department, position, permissions FROM staff WHERE username=?;";

        IPersistenceService db = mock(IPersistenceService.class);
        IUserRepository repo = new UserRepository(db);
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
        
        when(db.executeQuery(usernameListSql, "1")).thenReturn(usernameList);
        when(db.executeQuery(inspectorDetailsSql, username)).thenReturn(inspector);
        when(db.executeQuery(anotherInspectorDetailsSql, anotherUsername)).thenReturn(anotherInspector);

        List<Staff> response = repo.getInspectors();

        assertTrue(response != null);
        verify(db).executeQuery(usernameListSql, "1");
        verify(db).executeQuery(inspectorDetailsSql, username);
        verify(db).executeQuery(anotherInspectorDetailsSql, anotherUsername);
    }

    public void testInsertUser() throws Exception {
        String sql = "INSERT INTO staff VALUES(?, ?, ?, ?, ?, -1, 0, ?);";
        String username = "testUser";
        String firstName = "test";
        String lastName = "user";
        String department = "IT";
        String position = "Test";
        Permission permission = Permission.ADMIN;
        String strPermission = "0";

        Staff user = new Staff(username, firstName, lastName, department,
                position, permission);
        IPersistenceService db = mock(IPersistenceService.class);
        IUserRepository repo = new UserRepository(db);
    
        repo.insertUser(user);

        verify(db).executeUpdate(sql, username, firstName, lastName, department,
                position, strPermission);
    }

    public void testUpdateUser() throws Exception {
        String sql = "UPDATE staff SET firstName=?, lastName=?, department=?, " +
            "position=?, permissions=? WHERE username=?;";
        String username = "testUser";
        String firstName = "test";
        String lastName = "user";
        String department = "IT";
        String position = "Test";
        Permission permission = Permission.ADMIN;
        String strPermission = "0";

        Staff user = new Staff(username, firstName, lastName, department,
                position, permission);
        IPersistenceService db = mock(IPersistenceService.class);
        IUserRepository repo = new UserRepository(db);
    
        repo.updateUser(user);

        verify(db).executeUpdate(sql, firstName, lastName, department, position, 
                strPermission, username);
    }

    public void testDeleteUser() throws Exception {
        String sql = "DELETE FROM staff WHERE username=?;";
        String username = "testUser";

        IPersistenceService db = mock(IPersistenceService.class);
        IUserRepository repo = new UserRepository(db);
    
        repo.deleteUser(username);

        verify(db).executeUpdate(sql, username);
    }

    public void testSetPassword() throws Exception {
        String sql = "UPDATE staff SET passwordHash=?, salt=? WHERE username=?;";
        String username = "testUser";
        int passwordHash = 1234;

        IPersistenceService db = mock(IPersistenceService.class);
        IUserRepository repo = new UserRepository(db);
    
        repo.setPassword(username, passwordHash);

        verify(db).executeUpdate(eq(sql), anyString(), anyString(), eq(username));
    }
}
