package com.kritsit.casetracker.server.domain.services;

import java.io.IOException;
//import java.util.Map;

public interface IClientConnectionService {
    void run();
    void setConnectedClient(String connectedClient);
    String getConnectedClient();
    String login(String username, int passwordHash);
    void close() throws IOException;
}
