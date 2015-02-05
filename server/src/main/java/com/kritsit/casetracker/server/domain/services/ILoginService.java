package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.datalayer.RowToModelParseException;
import com.kritsit.casetracker.server.domain.model.AuthenticationException;
import com.kritsit.casetracker.shared.domain.model.Staff;

public interface ILoginService {
    Staff login(String name, int passwordHash) throws RowToModelParseException, AuthenticationException;
}
