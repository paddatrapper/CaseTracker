package com.kritsit.casetracker.server.domain.services;

import java.io.IOException;
//import java.util.Map;

public interface IClientConnectionService {
    void run();
    void setConnectedClient(String connectedClient);
    String getConnectedClient();
    void close() throws IOException;
}
