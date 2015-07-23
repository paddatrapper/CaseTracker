package com.kritsit.casetracker.client.domain.services;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        //to be implemented
        return new InputToModelParseResult(false);
    }

}
