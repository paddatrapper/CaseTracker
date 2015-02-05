package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.shared.domain.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnection implements IConnectionService {
    private Socket connectionSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    //private DataInputStream dataIn;
    //private DataOutputStream dataOut;
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
            out = new ObjectOutputStream(connectionSocket.getOutputStream());
            in = new ObjectInputStream(connectionSocket.getInputStream());
            //dataIn = new DataInputStream(connectionSocket.getInputStream());
            //dataOut = new DataOutputStream(connectionSocket.getOutputStream());
            out.writeObject("connect##::##" + getHostName());
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
            out.writeObject("close");
            out.flush();
            in.close();
            out.close();
            //dataIn.close();
            //dataOut.close();
        }
    }

    public boolean login(String username, int hash) {
        try {
            out.writeObject("login##::##" + username + "##::##" + hash);
            out.flush();
            Response reply = (Response) in.readObject();
            return reply.getMessage() == 200;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
