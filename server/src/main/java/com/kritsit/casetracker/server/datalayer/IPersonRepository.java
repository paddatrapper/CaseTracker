package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Person;
import com.kritsit.casetracker.shared.domain.model.Defendant;

import java.util.List;

public interface IPersonRepository {
    Person getComplainant(String caseNumber) throws RowToModelParseException;
    Defendant getDefendant(String caseNumber) throws RowToModelParseException;
}
