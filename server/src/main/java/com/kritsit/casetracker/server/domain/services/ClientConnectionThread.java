package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.datalayer.ICaseRepository;
import com.kritsit.casetracker.server.datalayer.IPersonRepository;
import com.kritsit.casetracker.server.datalayer.IUserRepository;
import com.kritsit.casetracker.server.datalayer.RepositoryFactory;
import com.kritsit.casetracker.server.datalayer.RowToModelParseException;
import com.kritsit.casetracker.server.domain.model.AuthenticationException;
import com.kritsit.casetracker.shared.domain.Request;
import com.kritsit.casetracker.shared.domain.Response;
import com.kritsit.casetracker.shared.domain.model.Case;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientConnectionThread implements Runnable, IClientConnectionService {
    private final Logger logger = LoggerFactory.getLogger(ClientConnectionThread.class);
    private Socket socket = null;
    private String connectedClient;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientConnectionThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            logger.debug("Connection thread started");
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            Request request;
            while ((request = (Request) in.readObject()) != null) {
                switch (request.getCommand()) {
                    case "connect" : {
                        setConnectedClient(request.getArguments().get(0).toString());
                        logger.info("{} has connected", connectedClient);
                        break;
                    }
                    case "login" : {
                        try {
                            logger.debug("Login requested");
                            IUserRepository repository = RepositoryFactory.getUserRepository();
                            ILoginService login = new Login(repository);
                            Staff user = login.login(request.getArguments().get(0).toString(),
                                    Integer.parseInt(request.getArguments().get(1).toString()));

                            Response dto = new Response(200, user);
                            writeResponse(dto);
                        } catch (AuthenticationException ex){
                            Response dto = new Response(401, null);
                            writeResponse(dto);
                        }
                        break;
                    }
                    case "getCases" : {
                        ICaseRepository caseRepo = RepositoryFactory.getCaseRepository();
                        List<Case> cases;
                        if (request.getArguments().size() == 0) {
                            logger.debug("All cases requested");
                            cases = caseRepo.getCases();
                        } else {
                            Staff user = (Staff) request.getArguments().get(0);
                            logger.debug("Cases requested for user {}", user);
                            cases = caseRepo.getCases(user);
                        }
                        Response dto = new Response(200, cases);
                        writeResponse(dto);
                        break;
                    }
                    case "getInspectors" : {
                        logger.debug("Inspectors requested");
                        IUserRepository userRepo = RepositoryFactory.getUserRepository();
                        List<Staff> inspectors = userRepo.getInspectors();
                        Response dto = new Response(200, inspectors);
                        writeResponse(dto);
                        break;
                    }
                    case "getLastCaseNumber" : {
                        logger.debug("Last case number requested");
                        ICaseRepository caseRepo = RepositoryFactory.getCaseRepository();
                        String caseNumber = caseRepo.getLastCaseNumber();
                        Response dto = new Response(200, caseNumber);
                        writeResponse(dto);
                        break;
                    }
                    case "addCase" : {
                        Case c = (Case) request.getArguments().get(0);
                        logger.debug("Requested to add case: {}", c);
                        ICaseRepository caseRepo = RepositoryFactory.getCaseRepository();
                        caseRepo.insertCase(c);
                        Response dto = new Response(200, "Case added");
                        writeResponse(dto);
                        break;
                    }
                    case "addUser" : {
                        Staff user = (Staff) request.getArguments().get(0);
                        logger.debug("Requested to add user: {}", user);
                        IUserRepository userRepo = RepositoryFactory.getUserRepository();
                        userRepo.insertUser(user);
                        Response dto = new Response(200, "User added");
                        writeResponse(dto);
                        break;
                    }
                    case "deleteUser" : {
                        String username = (String) request.getArguments().get(0);
                        logger.debug("Requested to delete user: {}", username);
                        IUserRepository userRepo = RepositoryFactory.getUserRepository();
                        userRepo.deleteUser(username);
                        Response dto = new Response(200, "User deleted");
                        writeResponse(dto);
                        break;
                    }
                    case "changePassword" : {
                        String username = (String) request.getArguments().get(0);
                        int passHash = (int) request.getArguments().get(1);
                        logger.debug("Requested to change password for {}", username);
                        IUserRepository userRepo = RepositoryFactory.getUserRepository();
                        userRepo.setPassword(username, passHash);
                        Response dto = new Response(200, "Password changed");
                        writeResponse(dto);
                        break;
                    }
                    case "close" : {
                        close();
                        logger.info("{} has disconnected", connectedClient);
                        return;
                    }
                }
            }
        } catch (IOException | RowToModelParseException | ClassNotFoundException ex) {
            logger.error("An error occured", ex);
            Response dto = new Response(500, ex.getMessage());
            try {
                writeResponse(dto);
            } catch (IOException e) {
                logger.error("Unable to write to client", e);
            }
        }
    }

    public void setConnectedClient(String connectedClient) {
        this.connectedClient = connectedClient;
    }

    public String getConnectedClient() {
        return connectedClient;
    }

    public void close() throws IOException {
        try {
            logger.info("Closing connection");
            in.close();
            out.close();
            socket.close();
        } catch (NullPointerException ex) {
            logger.error("Socket is not connected");
        }
    }

    private void writeResponse(Response dto) throws IOException {
        out.writeObject(dto);
        out.flush();
    }
}
