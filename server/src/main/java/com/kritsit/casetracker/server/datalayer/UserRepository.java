package com.kritsit.casetracker.server.datalayer;

import java.util.Map;

import com.kritsit.casetracker.server.domain.model.AuthenticationException;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Staff;

public class UserRepository implements IUserRepository {
	
	private final IPersistenceService db;
	
	public UserRepository(IPersistenceService db){
		this.db = db;
	}
	
    public long getPasswordSaltedHash(String username) throws RowToModelParseException {
    	try {
	        String sql = "SELECT passwordHash FROM staff WHERE username=\'" 
	            + username + "\';";
	        Map<String, String> rs = db.executeQuery(sql);
	        
	        if(rs == null) {
	        	throw new AuthenticationException();
	        }
	        
	        return Long.parseLong(rs.get("passwordHash"));
    	}
    	catch(Exception e){
    		//LOG
            e.printStackTrace();
    		throw new RowToModelParseException("Error retrieving password hash from database for username: " + username);
    	}
    }

    public long getSalt(String username) throws RowToModelParseException {
    	try {
	        String sql = "SELECT salt FROM staff WHERE username=\'" + username + "\';";
	        Map<String, String> rs = db.executeQuery(sql);
	        
	        if(rs == null) {
	        	throw new AuthenticationException();
	        }
	        
	        return Long.parseLong(rs.get("salt"));
    	}
    	catch(Exception e){
    		//LOG
            e.printStackTrace();
    		throw new RowToModelParseException("Error retrieving salt from database for username: " + username);
    	}
    }
	
	public Staff getUserDetails(String username) throws RowToModelParseException {
		try {
	        String sql = "SELECT firstName, lastName, department, position, permissions FROM staff "
	            + "WHERE username=\'" + username + "\';";
	        Map<String, String> details = db.executeQuery(sql);
	        
	        Permission permission = Permission.values()[Integer.parseInt(details.get("permissions"))];
	        
	        return new Staff(username, details.get("firstName"), details.get("lastName"), 
	        			     details.get("department"), details.get("position"), permission);
		}
		catch(Exception e){
			//Need to log the internal exception (e.Message)
			throw new RowToModelParseException("Error retrieving user details from database for username: " + username);
		}
    }
}
