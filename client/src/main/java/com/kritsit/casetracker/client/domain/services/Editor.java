package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Staff;

import java.util.List;

public class Editor implements IEditorService {
    Staff user;
    IConnectionService connection;

    public Editor(Staff user, IConnectionService connection) {
        this.user = user;
        this.connection = connection;
    }

    public Staff getUser() {
        return user;
    }

    public List<Case> getCases() {
        return connection.getCases(null);
    }
}
