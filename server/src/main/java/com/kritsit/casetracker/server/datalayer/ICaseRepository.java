package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Staff;

import java.util.List;

public interface ICaseRepository {
    List<Case> getCases() throws RowToModelParseException;
    List<Case> getCases(Staff inspector) throws RowToModelParseException;
    String getLastCaseNumber() throws RowToModelParseException;
    void insertCase(Case c) throws RowToModelParseException;
}
