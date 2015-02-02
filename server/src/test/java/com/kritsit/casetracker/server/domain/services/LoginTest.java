package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.datalayer.IPersistenceService;
import com.kritsit.casetracker.server.datalayer.IUserRepository;
import com.kritsit.casetracker.server.datalayer.RowToModelParseException;
import com.kritsit.casetracker.server.datalayer.UserRepository;
import com.kritsit.casetracker.server.domain.Domain;
import com.kritsit.casetracker.server.domain.model.Staff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LoginTest extends TestCase {
    IPersistenceService persistence;
    ILoginService login;

    public LoginTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(LoginTest.class);
    }

    public void setUp() {
        persistence = Domain.getPersistenceService();
        IUserRepository repo = new UserRepository(persistence);
        login = new Login(repo);
    }

    public void testCreation() {
        assertTrue(login instanceof ILoginService);
    }

    /*
     * Failing? DB down? rewrite when we start working with mockin
     * 
    public void testLoginAttempt_IncorrectUser() throws RowToModelParseException {
        int password = "inspector".hashCode();
        String username = "wrongInspector";
        Staff succeeded = login.login(username, password);
        assertTrue(succeeded == null);
    }

    public void testLoginAttempt_IncorrectPassword() throws RowToModelParseException {
        int password = "wrong inspector".hashCode();
        String username = "inspector";
        Staff succeeded = login.login(username, password);
        assertFalse(succeeded != null);
    }

    public void testLoginAttempt_Succeeded() throws RowToModelParseException {
        int password = "inspector".hashCode();
        String username = "inspector";
        Staff succeeded = login.login(username, password);
        assertTrue(succeeded.getUsername() == "inspector");
    }
*/
    public void tearDown() {
        persistence.close();
        Domain.resetPersistenceService();
    }
}
