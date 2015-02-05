package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Staff;

public interface IUserRepository {
	long getPasswordSaltedHash(String username) throws RowToModelParseException;
	long getSalt(String username) throws RowToModelParseException;
	Staff getUserDetails(String username) throws RowToModelParseException;
}
