package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.server.domain.model.Staff;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UserRepositoryTest extends TestCase {

    public UserRepositoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(UserRepositoryTest.class);
    }

	public void testGetUserDetails() throws Exception {
		IPersistenceService db = new DatabasePersistence();
		UserRepository repo = new UserRepository(db);
		
        String username = "inspector";
        Staff response = repo.getUserDetails(username);
        assertTrue(response != null);
    }

	public void testGetSalt() throws Exception{
		String username = "inspector";
		long expectedSalt = -5922475058261058398L;
		
		IPersistenceService db = new DatabasePersistence();
		UserRepository repo = new UserRepository(db);
		long actualSalt = repo.getSalt(username);
		
		assertTrue(expectedSalt == actualSalt);
	}
	
    public void testGetPasswordSaltedHash() throws Exception {
        String username = "inspector";
        long expectedSalt = -5922475058261058398L;
        long expectedPasswordSaltedHash = "inspector".hashCode() + expectedSalt;
        
        IPersistenceService db = new DatabasePersistence();
		UserRepository repo = new UserRepository(db);
        long passwordSaltedHash = repo.getPasswordSaltedHash(username);
        
        assertTrue(expectedPasswordSaltedHash == passwordSaltedHash);
    }
}
