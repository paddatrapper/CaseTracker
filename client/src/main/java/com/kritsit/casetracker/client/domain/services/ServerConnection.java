package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Staff;
import com.kritsit.casetracker.shared.domain.Request;
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
import java.util.ArrayList;
import java.util.List;

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
            List<String> argument = new ArrayList<>();
            argument.add(getHostName());
            Request request = new Request("connect", argument);
            writeOut(request);
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
            Request request = new Request("close");
            out.writeObject(request);
            out.flush();
            in.close();
            out.close();
        }
    }

    public boolean login(String username, int hash) {
        Response response = getLoginResponse(username, hash);
        if (response == null) {
            return false;
        }
        return response.getStatus() == 200;
    }

    public Staff getUser(String username, int hash) {
        Response response = getLoginResponse(username, hash);
        return (Staff) response.getBody();
    }

    private Response getLoginResponse(String username, int hash) {
        try {
            List<String> arguments = new ArrayList<>();
            arguments.add(username);
            arguments.add(String.valueOf(hash));
            Request request = new Request("login", arguments);
            return getResponse(request);
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Unable to retrieve login response", ex);
            return null;
        }
    }

    public List<Case> getCases(Staff user) {
        try {
            Request request;
            if (user == null) {
                request = new Request("getCases");
            } else {
                List<Staff> argument = new ArrayList<>();
                argument.add(user);
                request = new Request("getCases", argument); 
            }
            Response response = getResponse(request);
            return (List<Case>) response.getBody();
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Unable to get cases", ex);
            return null;
        }
    }

    private Response getResponse(Request request) throws IOException, ClassNotFoundException {
        writeOut(request);
        Response response = (Response) in.readObject();
        return response;
    }

    private void writeOut(Request request) throws IOException {
        out.writeObject(request);
        out.flush();
    }
}
