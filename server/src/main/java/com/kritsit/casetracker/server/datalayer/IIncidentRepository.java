package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Incident;

import java.util.List;

public interface IIncidentRepository {
    Incident getIncident(String caseId) throws RowToModelParseException;
}
