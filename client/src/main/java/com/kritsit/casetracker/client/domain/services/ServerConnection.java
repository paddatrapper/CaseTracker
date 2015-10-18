package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Evidence;
import com.kritsit.casetracker.shared.domain.model.Staff;
import com.kritsit.casetracker.shared.domain.FileSerializer;
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
        return response.isSuccessful();
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
            if (response.isSuccessful()) {
                return (List<Case>) response.getBody();
            } else {
                logger.error("Unable to get cases. Code {} - {}",
                        response.getStatus(),
                        response.getBody().toString());
                return null;
            }
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

    public List<Staff> getInspectors() {
        try {
            Request request = new Request("getInspectors");
            Response response = getResponse(request);
            return (List<Staff>) response.getBody();
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Unable to get inspectors", ex);
            return null;
        }
    }

    public List<Staff> getStaff() {
        try {
            Request request = new Request("getStaff");
            Response response = getResponse(request);
            return (List<Staff>) response.getBody();
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Unable to get staff", ex);
            return null;
        }
    }

    public String getLastCaseNumber() {
        try {
            Request request = new Request("getLastCaseNumber");
            Response response = getResponse(request);
            return (String) response.getBody();
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Unable to get last case number", ex);
            return "0000-00-0000";
        }
    }

    public boolean addCase(Case c) {
        try {
            serializeEvidence(c.getEvidence());
            List<Case> arguments = new ArrayList<>();
            arguments.add(c);
            Request request = new Request("addCase", arguments);
            Response response = getResponse(request);
            if (!response.isSuccessful()) {
                logger.error("Unable to upload cases. Code {} - {}",
                        response.getStatus(),
                        response.getBody().toString());
            }
            return response.isSuccessful();
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Unable to upload case", ex);
            return false;
        }
    }
    
    public boolean editCase(Case c) {
        try{
            List<Case> arguments = new ArrayList<>();
            arguments.add(c);
            Request request = new Request("editCase", arguments);
            Response response = getResponse(request);
            if (!response.isSuccessful()) {
                logger.error("Unable to update case. Code {} - {}",
                        response.getStatus(),
                        response.getBody().toString());
            }
            return response.isSuccessful();
         } catch (IOException | ClassNotFoundException ex) {
            logger.error("Unable to update user", ex);
            return false;
         }
    }
    
    public boolean addUser(Staff s){
       try{
        List<Staff> arguments = new ArrayList<>();
        arguments.add(s);
        Request request = new Request("addUser", arguments);
        Response response = getResponse(request);
        if (!response.isSuccessful()) {
            logger.error("Unable to add user. Code {} - {}",
                    response.getStatus(),
                    response.getBody().toString());
        }
        return response.isSuccessful();
       }
     catch (IOException | ClassNotFoundException ex) {
        logger.error("Unable to add user", ex);
        return false;
       }
    }
    
    public boolean editUser(Staff s) {
        try{
            List<Staff> arguments = new ArrayList<>();
            arguments.add(s);
            Request request = new Request("editUser", arguments);
            Response response = getResponse(request);
            if (!response.isSuccessful()) {
                logger.error("Unable to update user. Code {} - {}",
                        response.getStatus(),
                        response.getBody().toString());
            }
            return response.isSuccessful();
         } catch (IOException | ClassNotFoundException ex) {
            logger.error("Unable to update user", ex);
            return false;
         }
    }
    
    public boolean deleteUser(String username){
        try{
           List<Object> arguments = new ArrayList<>();
           arguments.add(username);
           Request request = new Request("deleteUser", arguments);
           Response response = getResponse(request);
           if (!response.isSuccessful()) {
               logger.error("Unable to delete user. Code {} - {}",
                       response.getStatus(),
                       response.getBody().toString());
           }
           return response.isSuccessful();
        }
        catch (IOException | ClassNotFoundException ex) {
            logger.error("Unable to delete user", ex);
            return false;
        }
    }
    
    private void serializeEvidence(List<Evidence> evidence) throws IOException {
        FileSerializer serializer = new FileSerializer();
        for (Evidence e : evidence) {
            byte[] file = serializer.serialize(e.getLocalFile());
            e.setByteFile(file);
        }
    }
    
    public boolean resetPassword(String username, int hashedRandomPass) {
        try{
            List<Object> arguments = new ArrayList<>();
            arguments.add(username);
            arguments.add(hashedRandomPass);
            Request request = new Request("changePassword", arguments);
            Response response = getResponse(request);
            if (!response.isSuccessful()) {
                logger.error("Unable to reset password. Code {} - {}",
                        response.getStatus(),
                        response.getBody().toString());
            }
            return response.isSuccessful();
         } catch (IOException | ClassNotFoundException ex) {
            logger.error("Unable to reset password", ex);
            return false;
         }
    }

    public boolean changePassword(String username, int currentHashedPass, int newHashedPass) {
        if (login(username, currentHashedPass)) {
            return resetPassword(username, newHashedPass);
        } else {
            logger.info("Unable to change password - incorrect login attempt");
            return false;
        }
    }

}
