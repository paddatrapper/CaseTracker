package com.kritsit.casetracker.server.domain.services;

import java.io.IOException;

public interface IClientConnectionService {
    void run();
    void setConnectedClient(String connectedClient);
    String getConnectedClient();
    void close() throws IOException;
}
