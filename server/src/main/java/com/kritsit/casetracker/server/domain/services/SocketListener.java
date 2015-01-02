package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.domain.Domain;
import com.kritsit.casetracker.server.domain.InputInvalidException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketListener implements IListeningService {
    private boolean listening;
    private ServerSocket serverSocket;
    private Socket socket;
    private IPersistenceService persistence;

    public SocketListener() {
        listening = false;
    }

    public void listen(int port) throws InputInvalidException, IOException {
        listening = true;
        if (port < 1 || port > 65535) {
            throw new InputInvalidException("Port number not in range");
        }
        serverSocket = new ServerSocket(port);
        persistence = Domain.getPersistenceService();
        if (persistence.isOpen()) {
            while (listening) {
                socket = serverSocket.accept();
            }
        }
    }

    public boolean isListening() {
        return listening;
    }

    public void stop() throws IOException {
        try {
            serverSocket.close();
            listening = false;
        } catch (NullPointerException ex) {}
    }
}
