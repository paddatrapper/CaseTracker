package com.kritsit.casetracker.client.domain.services;

import java.util.List;
import java.util.Map;

import com.kritsit.casetracker.shared.domain.model.Staff;

public interface IAdministratorService {
    
    Staff getUser();
    List<Staff> getInspectors();
    InputToModelParseResult addUser(Map<String, Object> inputMap);
    InputToModelParseResult editUser(Map<String, Object> inputMap);
    int deleteUser(Map<String, Object> inputMap);

}
