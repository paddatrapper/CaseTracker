package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.shared.domain.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final Logger logger = LoggerFactory.getLogger(ServerConnection.class);
    private Socket connectionSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean open;

    public ServerConnection() {
        open = false;
    }
    
    public boolean open(String host, int port) throws IllegalArgumentException {
        if (port < 0 || port > 65535) {
            logger.debug("Port {} out of bounds", String.valueOf(port));
            throw new IllegalArgumentException("Port must be in range");
        }
        try {
            logger.info("Connecting to server");
            connectionSocket = new Socket(host, port);
            out = new ObjectOutputStream(connectionSocket.getOutputStream());
            in = new ObjectInputStream(connectionSocket.getInputStream());
            out.writeObject("connect##::##" + getHostName());
            out.flush();
            open = true;
            logger.debug("Connected to server");
        } catch (UnknownHostException ex) {
            logger.debug("Host not found", ex);
            throw new IllegalArgumentException("Host not found");
        } catch (IOException ex) {
            logger.error("Unable to open connection with server", ex);
        }
        return open;
    }

    private String getHostName() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostName();
        } catch (UnknownHostException ex) {
            logger.error("Unable to find host name", ex);
            return "Unknown host";
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void close() throws IOException {
        if (open) {
            logger.debug("Closing connection with server");
            out.writeObject("close");
            out.flush();
            in.close();
            out.close();
        }
    }

    public boolean login(String username, int hash) {
        try {
            out.writeObject("login##::##" + username + "##::##" + hash);
            out.flush();
            Response reply = (Response) in.readObject();
            return reply.getStatus() == 200;
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Unable to log in", ex);
            return false;
        }
    }
}
