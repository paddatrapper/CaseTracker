package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Staff;

import java.util.List;

public interface IEditorService {
    void showUI();
    Staff getUser();
    List<Case> getCases();
}
