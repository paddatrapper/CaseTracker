package com.kritsit.casetracker.client.domain.services;

import java.io.File;
import java.io.IOException;

public interface IUpdateService {
    boolean checkForUpdate(String currentVersion);
    File update() throws IOException;
    void launch(File jar) throws IOException;
}
