package com.kritsit.casetracker.server.domain.services;

import java.io.IOException;

public interface IUpdateService {
    boolean isUpdateRequired(String currentVersion) throws IOException;
    byte[] getUpdate() throws IOException;
}
