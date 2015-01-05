package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.domain.Domain;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnectionThread implements Runnable, IClientConnectionService {
    private Socket socket = null;
    private final IPersistenceService persistence;
    private String connectedClient;
    private BufferedReader in;
    private PrintWriter out;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    public ClientConnectionThread(Socket socket) throws IOException {
        this.socket = socket;
        persistence = Domain.getPersistenceService();
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        dataIn = new DataInputStream(socket.getInputStream());
        dataOut = new DataOutputStream(socket.getOutputStream());
    }

    public void run() {
    }

    public void setConnectedClient(String connectedClient) {
        this.connectedClient = connectedClient;
    }

    public String getConnectedClient() {
        return connectedClient;
    }

    public void close() throws IOException {
        in.close();
        out.close();
        dataIn.close();
        dataOut.close();
        socket.close();
    }
}
