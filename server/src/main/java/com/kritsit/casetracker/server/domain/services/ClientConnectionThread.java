package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.datalayer.IPersistenceService;
import com.kritsit.casetracker.server.datalayer.IUserRepository;
import com.kritsit.casetracker.server.datalayer.RowToModelParseException;
import com.kritsit.casetracker.server.datalayer.UserRepository;
import com.kritsit.casetracker.server.domain.Domain;
import com.kritsit.casetracker.server.domain.model.AuthenticationException;
import com.kritsit.casetracker.shared.domain.Response;
import com.kritsit.casetracker.shared.domain.model.Staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnectionThread implements Runnable, IClientConnectionService {
    private final Logger logger = LoggerFactory.getLogger(ClientConnectionThread.class);
    private Socket socket = null;
    private IPersistenceService persistence;
    private String connectedClient;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    public ClientConnectionThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            logger.debug("Connection thread started");
            persistence = Domain.getPersistenceService();
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
            String input, output;
            while ((input = (String) in.readObject()) != null) {
                String[] data = input.split("##::##");
                switch (data[0]) {
                    case "connect": {
                        setConnectedClient(data[1]);
                        logger.info("{} has connected", connectedClient);
                        break;
                    }
                    case "login": {
                        logger.debug("Login requested");
                    	//These constructions will be handled by the DI container
                    	IUserRepository repository = new UserRepository(persistence);
                        ILoginService login = new Login(repository);

                        Staff user = login.login(data[1], Integer.parseInt(data[2]));
                        
                        //This probably needs to be demanded to a factory method
                        Response dto = new Response(200, user);
                         
                        out.writeObject(dto);
                        out.flush();
                        break;
                    }
                    case "close": {
                        close();
                        logger.info("{} has disconnected", connectedClient);
                        return;
                    }
                }
            }
        } 
        catch (AuthenticationException ex){
        	Response dto = new Response(401, null);
        	try {
				out.writeObject(dto);
			} catch (IOException e) {
                logger.error("Unable to write to client", e);
			}
        }
        catch (IOException | RowToModelParseException | ClassNotFoundException ex) {
            logger.error("An error occured", ex);
            //Either deal with exception generically here or specifically in each call
            Response dto = new Response(500, null);
            try {
				out.writeObject(dto);
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
            dataIn.close();
            dataOut.close();
            socket.close();
        } catch (NullPointerException ex) {
            logger.error("Socket is not connected");
        }
    }
}
