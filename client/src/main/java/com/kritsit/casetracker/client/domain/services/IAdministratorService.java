package com.kritsit.casetracker.client.domain.services;

import java.util.Map;

import com.kritsit.casetracker.shared.domain.model.Staff;

public interface IAdministratorService {
    
    Staff getUser();
    InputToModelParseResult addUser(Map<String, Object> inputMap);

}
