package com.kritsit.casetracker.client.domain.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kritsit.casetracker.client.validation.IValidator;
import com.kritsit.casetracker.client.validation.StringValidator;
import com.kritsit.casetracker.shared.domain.model.Permission;
import com.kritsit.casetracker.shared.domain.model.Staff;

public class Administrator implements IAdministratorService {

    private final Logger logger = LoggerFactory.getLogger(Administrator.class);
    
    Staff user;
    IConnectionService connection;

    public Administrator(Staff user, IConnectionService connection) {
        this.user = user;
        this.connection = connection;
    }
    
    public Staff getUser() {
        return user;    
    }

    public InputToModelParseResult addUser(Map<String, Object> inputMap) {
        if (inputMap == null || inputMap.isEmpty()) {
            logger.debug("InputMap empty or null. Aborting");
            return new InputToModelParseResult(false, "Required information missing");
        }
        
        logger.info("Add user {}", inputMap.get("username"));
        
        InputToModelParseResult result = validate(inputMap);
        
        if (!result.isSuccessful()) {
            return result;
        }
        
        Staff s = parseUser(inputMap);
        logger.debug("Adding user to server");
        boolean isAdded = connection.addUser(s);
        String reason = (isAdded) ? "User added successfully" :
            "Unable to add user. Please see log for details";
         
        InputToModelParseResult uploaded = new InputToModelParseResult(isAdded, reason);
        return uploaded;
    }
    
    private Staff parseUser(Map<String, Object> inputMap){
        logger.info("Parsing user {}", inputMap.get("username"));
        String username = (String) inputMap.get("username");
        String firstname = (String) inputMap.get("firstname");
        String lastname = (String) inputMap.get("lastname");
        String department = (String) inputMap.get("department");
        String position = (String) inputMap.get("position");
        Permission permission;
        try{
            permission = Permission.valueOf((String) inputMap.get("permission"));
        }
        catch(IllegalArgumentException e){
            permission = null;
        }
        Staff staff = new Staff(username, firstname, lastname, department, position, permission);
        return staff;
    }
    
    public int deleteUser(Map<String, Object> inputMap){
        if (inputMap == null || inputMap.isEmpty()) {
            logger.debug("InputMap empty or null. Aborting");
            return 400;
        }
        logger.info("Delete user {}", inputMap.get("username"));
        
        boolean isDeleted = connection.deleteUser(inputMap.get("username").toString());
        
        if(isDeleted) return 200; 
        else return 500;
    }

    public List<Staff> getInspectors() {
        return connection.getInspectors();
    }

    public InputToModelParseResult editUser(Map<String, Object> inputMap) {
        if (inputMap == null || inputMap.isEmpty()) {
            logger.debug("InputMap empty or null. Aborting");
            return new InputToModelParseResult(false, "Required information missing");
        }
        
        logger.info("Edit user {}", inputMap.get("username"));
        
        InputToModelParseResult result = validate(inputMap);
        
        if (!result.isSuccessful()) {
            return result;
        }
        
        Staff s = parseUser(inputMap);
        logger.debug("Updating user on server");
        boolean isUpdated = connection.editUser(s);
        String reason = (isUpdated) ? "User updated successfully" :
            "Unable to update user. Please see log for details";
        
        InputToModelParseResult uploaded = new InputToModelParseResult(isUpdated, reason);
        return uploaded;
    }

    private InputToModelParseResult validate(Map<String, Object> inputMap){
        InputToModelParseResult result = new InputToModelParseResult(true);
        for(Map.Entry<String, Object> entry : inputMap.entrySet()){
            if(entry.getKey().equals("firstname")) continue;
            if(entry.getKey().equals("position")) continue;
            
            IValidator validator = new StringValidator();
            
            if(validator.validate(entry.getValue())){
                continue;
            } else{
                result.addFailedInput(entry.getKey());
            }
        }
        return result;
    }

    public int resetPassword(String username, int hashedRandomPass) {
        logger.info("Reseting password for user {}", username);
        boolean result = connection.resetPassword(username, hashedRandomPass);
        if(result){
            logger.info("Password reset succesfully");
            return 200;
        } else{
            logger.error("Error reseting user's password");
            return 500;
        }
    }
    
    public String randomPassword() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(40, random).toString(32);
    }
    
}
