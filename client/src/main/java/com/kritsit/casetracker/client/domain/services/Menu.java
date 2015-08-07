package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.shared.domain.model.Staff;

public class Menu implements IMenuService {
    
    private Staff user;
    private IConnectionService connectionService;
    
    public Menu(Staff user, IConnectionService connectionService){
        this.user=user;
        this.connectionService=connectionService;
    }

}
