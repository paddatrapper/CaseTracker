package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.datalayer.IUserRepository;
import com.kritsit.casetracker.server.datalayer.RowToModelParseException;
import com.kritsit.casetracker.server.domain.model.Staff;

public class Login implements ILoginService {
    private IUserRepository repository;

    public Login(IUserRepository repository) {
        this.repository = repository;
    }

    public Staff login(String username, int passwordHash) throws RowToModelParseException {
        long passwordSaltedHash = repository.getPasswordSaltedHash(username);
        long salt = repository.getSalt(username);
        long testPasswordSaltedHash = salt + passwordHash;
        boolean isAuthenticated = !(passwordSaltedHash == -1 || testPasswordSaltedHash != passwordSaltedHash);
        
        if(!isAuthenticated){
        	//throw custom exception?
        }
        
        return repository.getUserDetails(username);
    }
}
