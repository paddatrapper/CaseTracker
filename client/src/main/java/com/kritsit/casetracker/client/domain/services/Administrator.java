package com.kritsit.casetracker.client.domain.services;

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
        
        InputToModelParseResult result = new InputToModelParseResult(true);
        
        if (inputMap == null || inputMap.isEmpty()) {
            logger.debug("InputMap empty or null. Aborting");
            return new InputToModelParseResult(false, "Required information missing");
        }
        
        for(Map.Entry<String, Object> entry : inputMap.entrySet()){
             if(entry.getKey().equals("firstname")) continue;
             if(entry.getKey().equals("position")) continue;
             if(entry.getKey().equals("permission")&&entry.getValue() instanceof Permission) continue;
             IValidator validator = new StringValidator();
             if(validator.validate(entry.getValue())){
                 continue;
             }
             else{
                 result.addFailedInput(entry.getKey());
             }
        }
        
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
        Permission permission = (Permission) Permission.valueOf(String.valueOf(inputMap.get("permission")));
        Staff staff = new Staff(username, firstname, lastname, department, position, permission);
        return staff;
    }

    public List<Staff> getInspectors() {
        return connection.getInspectors();
    }

}
