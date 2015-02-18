package com.kritsit.casetracker.server.datalayer;

import java.util.List;
import java.util.Map;

import com.kritsit.casetracker.server.domain.model.AuthenticationException;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRepository implements IUserRepository {
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);	
	private final IPersistenceService db;
	
	public UserRepository(IPersistenceService db){
		this.db = db;
	}
	
    public long getPasswordSaltedHash(String username) throws RowToModelParseException {
    	try {
            logger.info("Fetching password salted hash for {}", username);
	        String sql = "SELECT passwordHash FROM staff WHERE username=\'" 
	            + username + "\';";
	        List<Map<String, String>> rs = db.executeQuery(sql);
	        
	        if(rs == null || rs.size() == 0) {
                logger.debug("{} does not exist", username);
	        	throw new AuthenticationException();
	        } 
	        return Long.parseLong(rs.get(0).get("passwordHash"));
    	}
    	catch(Exception e){
    		logger.error("Error retrieving password salted hash for {}", username, e);
    		throw new RowToModelParseException("Error retrieving password salted hash from database for username: " + username);
    	}
    }

    public long getSalt(String username) throws RowToModelParseException {
    	try {
            logger.info("Fetching salt for {}", username);
	        String sql = "SELECT salt FROM staff WHERE username=\'" + username + "\';";
	        List<Map<String, String>> rs = db.executeQuery(sql);
	        
	        if(rs == null || rs.size() == 0) {
                logger.debug("{} does not exist", username);
	        	throw new AuthenticationException();
	        }
	        
	        return Long.parseLong(rs.get(0).get("salt"));
    	}
    	catch(Exception e){
    		logger.error("Error retrieving salt for {}", username, e);
    		throw new RowToModelParseException("Error retrieving salt from database for username: " + username);
    	}
    }
	
	public Staff getUserDetails(String username) throws RowToModelParseException {
		try {
            logger.info("Fetching details for {}", username);
	        String sql = "SELECT firstName, lastName, department, position, permissions FROM staff "
	            + "WHERE username=\'" + username + "\';";
	        List<Map<String, String>> rs = db.executeQuery(sql);
	        Map<String, String> details = rs.get(0);

	        Permission permission = Permission.values()[Integer.parseInt(details.get("permissions"))];
	        
	        return new Staff(username, details.get("firstName"), details.get("lastName"), 
	        			     details.get("department"), details.get("position"), permission);
		}
		catch(Exception e){
    		logger.error("Error retrieving details for {}", username, e);
			throw new RowToModelParseException("Error retrieving user details from database for username: " + username);
		}
    }
}
