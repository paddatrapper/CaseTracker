package com.kritsit.casetracker.client.domain.services;

import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import com.kritsit.casetracker.shared.domain.model.Staff;
import com.kritsit.casetracker.shared.domain.model.Permission;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AdministratorTest extends TestCase {
    
    public AdministratorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(AdministratorTest.class);
    }

    public void testGetUser() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        Administrator administrator = new Administrator(user, connection);
        assertTrue(administrator.getUser() != null);
    }
    
    public void testAddUser_Null() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);

        InputToModelParseResult result = administrator.addUser(null); 

        assertFalse(result.isSuccessful());
        assertTrue("Required information missing".equals(result.getReason()));
    }
    
    public void testAddUser_NoData() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);

        InputToModelParseResult result = administrator.addUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertTrue("Required information missing".equals(result.getReason()));
    }
    
    public void testAddUser_usernameMissing() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        inputMap.put("username", "");
        inputMap.put("firstname", "John");
        inputMap.put("lastname", "Doe");
        inputMap.put("position", "admin");
        inputMap.put("permission", "ADMIN");
        
        InputToModelParseResult result = administrator.addUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertTrue("username required".equals(result.getReason()));
    }
    
    public void testAddUser_lastnameMissing() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        inputMap.put("username", "johndoe");
        inputMap.put("firstname", "John");
        inputMap.put("lastname", "");
        inputMap.put("position", "admin");
        inputMap.put("permission", "ADMIN");
        
        InputToModelParseResult result = administrator.addUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertTrue("lastname required".equals(result.getReason()));
    }
    
    public void testAddUser_permissionMissing() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        inputMap.put("username", "johndoe");
        inputMap.put("firstname", "John");
        inputMap.put("lastname", "Doe");
        inputMap.put("position", "admin");
        inputMap.put("permission", "");
        
        InputToModelParseResult result = administrator.addUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertTrue("permission required".equals(result.getReason()));
    }
    
    public void testAddUser_permissionAndLastnameMissing() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        inputMap.put("username", "johndoe");
        inputMap.put("firstname", "John");
        inputMap.put("lastname", "");
        inputMap.put("position", "admin");
        inputMap.put("permission", "");
        
        InputToModelParseResult result = administrator.addUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertTrue("permission and lastname required".equals(result.getReason()));
    }
   

    
}
