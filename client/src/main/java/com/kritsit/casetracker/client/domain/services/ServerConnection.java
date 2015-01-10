package com.kritsit.casetracker.client.domain.services;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnection implements IConnectionService {
    private Socket connectionSocket;
    private BufferedReader in;
    private PrintWriter out;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private boolean open;

    public ServerConnection() {
        open = false;
    }
    
    public boolean open(String host, int port) throws IllegalArgumentException {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Port must be in range");
        }
        try {
            connectionSocket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            out = new PrintWriter(connectionSocket.getOutputStream());
            dataIn = new DataInputStream(connectionSocket.getInputStream());
            dataOut = new DataOutputStream(connectionSocket.getOutputStream());
            out.println("connect##::##" + getHostName());
            out.flush();
            open = true;
        } catch (UnknownHostException ex) {
            throw new IllegalArgumentException("Host not found");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return open;
    }

    private String getHostName() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostName();
        } catch (UnknownHostException ex) {
            return "Unknown host";
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void close() throws IOException {
        if (open) {
            out.println("close");
            out.flush();
            in.close();
            out.close();
            dataIn.close();
            dataOut.close();
        }
    }

    public boolean login(String username, int hash) {
        try {
            out.println("login##::##" + username + "##::##" + hash);
            out.flush();
            String reply = in.readLine();
            return Boolean.parseBoolean(reply);
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
