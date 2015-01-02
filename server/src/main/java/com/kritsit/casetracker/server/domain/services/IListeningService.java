package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.domain.InputInvalidException;

import java.io.IOException;

public interface IListeningService {
    void listen(int port) throws IOException, InputInvalidException;
    boolean isListening();
    void stop() throws IOException;
}
