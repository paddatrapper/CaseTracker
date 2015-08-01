package com.kritsit.casetracker.client.domain.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    
    public void testEditUser_Null() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);

        InputToModelParseResult result = administrator.editUser(null); 

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
    
    public void testEditUser_NoData() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);

        InputToModelParseResult result = administrator.editUser(inputMap);          

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
        inputMap.put("department", "IT");
        inputMap.put("position", "admin");
        inputMap.put("permission", "ADMIN");
        
        InputToModelParseResult result = administrator.addUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertTrue("username required".equals(result.getReason()));
    }
    
    public void testEditUser_usernameMissing() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        inputMap.put("username", "");
        inputMap.put("firstname", "John");
        inputMap.put("lastname", "Doe");
        inputMap.put("department", "IT");
        inputMap.put("position", "admin");
        inputMap.put("permission", "ADMIN");
        
        InputToModelParseResult result = administrator.editUser(inputMap);          

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
        inputMap.put("department", "IT");
        inputMap.put("position", "admin");
        inputMap.put("permission", "ADMIN");
        
        InputToModelParseResult result = administrator.addUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertTrue("lastname required".equals(result.getReason()));
    }
    
    public void testEditUser_lastnameMissing() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        inputMap.put("username", "johndoe");
        inputMap.put("firstname", "John");
        inputMap.put("lastname", "");
        inputMap.put("department", "IT");
        inputMap.put("position", "admin");
        inputMap.put("permission", "ADMIN");
        
        InputToModelParseResult result = administrator.editUser(inputMap);          

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
        inputMap.put("department", "IT");
        inputMap.put("permission", "");
        
        InputToModelParseResult result = administrator.addUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertTrue("permission required".equals(result.getReason()));
    }
    
    public void testEituser_permissionMissing() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        inputMap.put("username", "johndoe");
        inputMap.put("firstname", "John");
        inputMap.put("lastname", "Doe");
        inputMap.put("position", "admin");
        inputMap.put("department", "IT");
        inputMap.put("permission", "");
        
        InputToModelParseResult result = administrator.editUser(inputMap);          

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
        inputMap.put("department", "IT");
        inputMap.put("permission", "");
        
        InputToModelParseResult result = administrator.addUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertTrue("permission and lastname required".equals(result.getReason()));
    }
    
    public void testEditUser_permissionAndLastnameMissing() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        inputMap.put("username", "johndoe");
        inputMap.put("firstname", "John");
        inputMap.put("lastname", "");
        inputMap.put("position", "admin");
        inputMap.put("department", "IT");
        inputMap.put("permission", "");
        
        InputToModelParseResult result = administrator.editUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertTrue("permission and lastname required".equals(result.getReason()));
    }
    
    public void testAddUser_allFieldsFilled() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        
        String username = "johndoe";
        String firstname = "John";
        String lastname = "Doe";
        String department = "IT";
        String position = "admin";
        Permission permission = Permission.ADMIN;
        
        Staff staff = new Staff(username, firstname, lastname, department, position, permission);
        
        inputMap.put("username", username);
        inputMap.put("firstname", firstname);
        inputMap.put("lastname", lastname);
        inputMap.put("position", position);
        inputMap.put("permission", permission);

        when(connection.addUser(any())).thenReturn(true);
        InputToModelParseResult result = administrator.addUser(inputMap);   
        
        assertTrue(result.isSuccessful());
        verify(connection).addUser(any());
    }
    
    public void testEditUser_allFieldsFilled() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        
        String username = "johndoe";
        String firstname = "John";
        String lastname = "Doe";
        String department = "IT";
        String position = "admin";
        Permission permission = Permission.ADMIN;
        
        Staff staff = new Staff(username, firstname, lastname, department, position, permission);
        
        inputMap.put("username", username);
        inputMap.put("firstname", firstname);
        inputMap.put("lastname", lastname);
        inputMap.put("position", position);
        inputMap.put("permission", permission);

        when(connection.editUser(any())).thenReturn(true);
        InputToModelParseResult result = administrator.editUser(inputMap);   
        
        assertTrue(result.isSuccessful());
        verify(connection).editUser(any());
    }
    
    public void testGetInspectors() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        
        administrator.getInspectors(); 

        verify(connection).getInspectors();
    }
    
    public void testDeletUser(){
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        assertTrue(administrator.deleteUser(null)==400);
        when(connection.deleteUser("johndoe")).thenReturn(true);
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("username", "johndoe");
        assertTrue(administrator.deleteUser(inputMap)==200);
        Map<String, Object> inputMap2 = new HashMap<>();
        inputMap.put("username", "other");
        assertTrue(administrator.deleteUser(inputMap)==500);
    }
    
    public void testResetPassword(){
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        when(connection.resetPassword("johndoe", 1234)).thenReturn(true);
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("username", "johndoe");
        inputMap.put("hashedRandomPass", 1234);
        assertTrue(administrator.resetPassword(inputMap)==200);
        Map<String, Object> inputMap2 = new HashMap<>();
        inputMap2.put("username", "johndoe");
        inputMap2.put("hashedRandomPass", 4321);
        assertTrue(administrator.resetPassword(inputMap2)==500);
    }
}
