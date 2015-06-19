package com.kritsit.casetracker.server.domain.services;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.server.datalayer.IPersistenceService;
import com.kritsit.casetracker.server.datalayer.IUserRepository;
import com.kritsit.casetracker.server.datalayer.RowToModelParseException;
import com.kritsit.casetracker.server.datalayer.UserRepository;
import com.kritsit.casetracker.server.domain.Domain;
import com.kritsit.casetracker.server.domain.model.AuthenticationException;
import com.kritsit.casetracker.shared.domain.model.Staff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LoginTest extends TestCase {
    IUserRepository repo;
    ILoginService login;

    public LoginTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(LoginTest.class);
    }

    public void setUp() throws RowToModelParseException {
        repo = mock(IUserRepository.class);
        when(repo.getPasswordSaltedHash("inspector")).thenReturn(-5922475058343094375L);
        when(repo.getSalt("inspector")).thenReturn(-5922475058261058398L);
        when(repo.getPasswordSaltedHash("wrongInspector")).thenReturn(-1L);
        when(repo.getSalt("wrongInspector")).thenReturn(-1L);
        Staff inspector = mock(Staff.class);
        when(repo.getUserDetails("inspector")).thenReturn(inspector);
        login = new Login(repo);
    }

    public void testCreation() {
        assertNotNull(login);
    }

    public void testLoginAttempt_IncorrectUser() throws RowToModelParseException {
    	try {
            int password = "inspector".hashCode();
            String username = "wrongInspector";
            login.login(username, password);
            fail("Exception was not thrown");
        } catch(AuthenticationException e){
            verify(repo).getPasswordSaltedHash("wrongInspector");
            verify(repo).getSalt("wrongInspector");
        } 
    }

    public void testLoginAttempt_IncorrectPassword() throws RowToModelParseException {
    	try {
            int password = "wrong inspector".hashCode();
            String username = "inspector";
            login.login(username, password);
            fail("Exception was not thrown");
        } catch(AuthenticationException e){
            verify(repo).getPasswordSaltedHash("inspector");
            verify(repo).getSalt("inspector");
        } 
    }

    public void testLoginAttempt_Succeeded() throws RowToModelParseException, AuthenticationException {
        int password = "inspector".hashCode();
        String username = "inspector";
        Staff succeeded = login.login(username, password);
        assertTrue(succeeded != null);
        verify(repo).getPasswordSaltedHash("inspector");
        verify(repo).getSalt("inspector");
    }
}
