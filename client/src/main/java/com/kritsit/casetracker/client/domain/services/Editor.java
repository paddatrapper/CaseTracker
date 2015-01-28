package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.model.Staff;

public class Editor implements IEditorService {
    Staff user;
    IConnectionService connection;

    public Editor(Staff user, IConnectionService connection) {
        this.user = user;
        this.connection = connection;
    }

    public void showUI() {
    }

    public Staff getUser() {
        return user;
    }
}
