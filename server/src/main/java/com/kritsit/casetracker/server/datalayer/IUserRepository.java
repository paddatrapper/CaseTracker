package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.server.domain.model.AuthenticationException;
import com.kritsit.casetracker.shared.domain.model.Staff;

import java.util.List;

public interface IUserRepository {
	long getPasswordSaltedHash(String username) throws RowToModelParseException, AuthenticationException;
	long getSalt(String username) throws RowToModelParseException, AuthenticationException;
	Staff getUserDetails(String username) throws RowToModelParseException, AuthenticationException;
    Staff getInvestigatingOfficer(String caseNumber) throws RowToModelParseException;
    List<Staff> getInspectors() throws RowToModelParseException;
    List<Staff> getStaff() throws RowToModelParseException;
    void insertUser(Staff user) throws RowToModelParseException;
    void updateUser(Staff user) throws RowToModelParseException;
    void deleteUser(String username) throws RowToModelParseException;
    void setPassword(String username, int hashPassword) throws RowToModelParseException;
}
