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
        assertNotNull(administrator.getUser());
    }
    
    public void testAddUser_Null() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);

        InputToModelParseResult result = administrator.addUser(null); 

        assertFalse(result.isSuccessful());
        assertEquals("Required information missing", result.getReason());
    }
    
    public void testEditUser_Null() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);

        InputToModelParseResult result = administrator.editUser(null); 

        assertFalse(result.isSuccessful());
        assertEquals("Required information missing", result.getReason());
    }
    
    public void testAddUser_NoData() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);

        InputToModelParseResult result = administrator.addUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertEquals("Required information missing", result.getReason());
    }
    
    public void testEditUser_NoData() {
        Map<String, Object> inputMap = new HashMap<>();
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);

        InputToModelParseResult result = administrator.editUser(inputMap);          

        assertFalse(result.isSuccessful());
        assertEquals("Required information missing", result.getReason());
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
        assertEquals("username required", result.getReason());
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
        assertEquals("username required", result.getReason());
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
        assertEquals("lastname required", result.getReason());
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
        assertEquals("lastname required", result.getReason());
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
        assertEquals("permission required", result.getReason());
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
        assertEquals("permission required", result.getReason());
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
        assertEquals("permission and lastname required", result.getReason());
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
        assertEquals("permission and lastname required", result.getReason());
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
        String permission = "ADMIN";
        
        Staff staff = new Staff(username, firstname, lastname, department, position, Permission.valueOf(permission));
        
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
        String permission = "ADMIN";
        
        Staff staff = new Staff(username, firstname, lastname, department, position, Permission.valueOf(permission));
        
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
    
    public void testGetStaff() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        
        administrator.getStaff(); 

        verify(connection).getStaff();
    }
    
    public void testDeletUser_Null(){
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);

        assertTrue(administrator.deleteUser(null) == 400);
    }
    
    public void testDeletUser_NotFound(){
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        Map<String, Object> inputMap2 = new HashMap<>();
        inputMap2.put("username", "other");

        assertTrue(administrator.deleteUser(inputMap2) == 500);
    }
    
    public void testDeletUser(){
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("username", "johndoe");

        when(connection.deleteUser("johndoe")).thenReturn(true);

        assertTrue(administrator.deleteUser(inputMap) == 200);
    }
    
    public void testResetPassword(){
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        IAdministratorService administrator = new Administrator(user, connection);

        when(connection.resetPassword("johndoe", 1234)).thenReturn(true);

        assertTrue(administrator.resetPassword("johndoe", 1234) == 200);
        assertTrue(administrator.resetPassword("johndoe", 4321) == 500);
    }
}
