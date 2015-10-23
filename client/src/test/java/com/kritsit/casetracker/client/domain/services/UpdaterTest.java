package com.kritsit.casetracker.client.domain.services;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.client.domain.ui.controller.UpdateController;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UpdaterTest extends TestCase {

    public UpdaterTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(UpdaterTest.class);
    }

    public void testCheckForUpdate() {
        String version = "0.1.2-ALPHA";
        IConnectionService connection = mock(IConnectionService.class);
        UpdateController controller = mock(UpdateController.class);
        IUpdateService updater = new Updater(connection, controller);

        when(connection.checkForUpdate(version)).thenReturn(true);

        boolean needUpdate = updater.checkForUpdate(version);

        assertTrue(needUpdate);
        verify(connection).checkForUpdate(version);
    }
}
