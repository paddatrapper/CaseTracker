package com.kritsit.casetracker.server.domain.services;

import java.io.IOException;

public interface IListeningService {
    void listen(int port) throws IOException, IllegalArgumentException;
    boolean isListening();
    void stop() throws IOException;
}
