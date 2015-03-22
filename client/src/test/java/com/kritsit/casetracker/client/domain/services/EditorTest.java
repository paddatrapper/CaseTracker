package com.kritsit.casetracker.client.domain.services;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Staff;
import com.kritsit.casetracker.shared.domain.model.Permission;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;

public class EditorTest extends TestCase {
    IEditorService editor;

    public EditorTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(EditorTest.class);
    }

    public void setUp() {
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        editor = new Editor(user, connection);
    }

    public void testCreation() {
        assertTrue(editor instanceof IEditorService);
    }

    public void testGetUser() {
        assertTrue(editor.getUser() != null);
    }

    public void testGetCases() {
        List<Case> caseList = new ArrayList<>();
        Case c = mock(Case.class);
        caseList.add(c);
        IConnectionService connection = mock(IConnectionService.class);
        Staff user = mock(Staff.class);
        editor = new Editor(user, connection);
       
        when(connection.getCases(null)).thenReturn(caseList);

        assertTrue(caseList.equals(editor.getCases()));
        verify(connection).getCases(null);
    }
}
