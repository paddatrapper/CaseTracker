package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.shared.domain.model.Staff;
import com.kritsit.casetracker.shared.domain.model.Permission;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class EditorTest extends TestCase {
    IEditorService editor;
    Staff user;

    public EditorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(EditorTest.class);
    }

    public void setUp() {
        IConnectionService connection = new ServerConnection();
        user = new Staff("inspector", "inspector", "inspector", "Inspectorate", "manager", Permission.EDITOR);
        editor = new Editor(user, connection);
    }

    public void testCreation() {
        assertTrue(editor instanceof IEditorService);
        assertTrue(user.equals(editor.getUser()));
    }
}
