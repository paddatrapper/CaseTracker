package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.domain.Domain;

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

    public void listen(int port) throws IllegalArgumentException, IOException {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Port number not in range");
        }
        serverSocket = new ServerSocket(port);
        persistence = Domain.getPersistenceService();
        persistence.open();
        if (persistence.isOpen()) {
            listening = true;
            System.out.println("Listening");
            while (listening) {
                socket = serverSocket.accept();
                ClientConnectionThread connection = new ClientConnectionThread(socket);
                Thread t = new Thread(connection);
                t.start();
            }
        }
    }

    public boolean isListening() {
        return listening;
    }

    public void stop() throws IOException {
        try {
            listening = false;
            serverSocket.close();
        } catch (NullPointerException ex) {}
    }
}
