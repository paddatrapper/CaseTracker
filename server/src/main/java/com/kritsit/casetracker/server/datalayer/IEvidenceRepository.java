package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Evidence;

import java.util.List;

public interface IEvidenceRepository {
    List<Evidence> getEvidence(String caseNumber) throws RowToModelParseException;
}
