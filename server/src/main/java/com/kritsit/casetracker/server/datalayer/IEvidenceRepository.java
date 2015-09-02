package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Evidence;

import java.util.List;

public interface IEvidenceRepository {
    List<Evidence> getEvidence(String caseNumber) throws RowToModelParseException;
    void insertEvidence(Evidence evidence, String caseNumber) throws RowToModelParseException;
    void updateEvidence(List<Evidence> evidence, String caseNumber) throws RowToModelParseException;
}
