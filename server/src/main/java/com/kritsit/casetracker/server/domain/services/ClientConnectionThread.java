package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.datalayer.IPersistenceService;
import com.kritsit.casetracker.server.datalayer.IUserRepository;
import com.kritsit.casetracker.server.datalayer.RowToModelParseException;
import com.kritsit.casetracker.server.datalayer.UserRepository;
import com.kritsit.casetracker.server.domain.Domain;
import com.kritsit.casetracker.server.domain.Response;
import com.kritsit.casetracker.server.domain.model.Staff;

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
        try {
            String input, output;
            while ((input = in.readLine()) != null) {
                String[] data = input.split("##::##");
                switch (data[0]) {
                    case "connect": {
                        setConnectedClient(data[1]);
                        System.out.println(connectedClient + " has connected");
                        break;
                    }
                    case "login": {
                    	//These constructions will be handled by the DI container
                    	IUserRepository repository = new UserRepository(persistence);
                        ILoginService login = new Login(repository);

                        Staff user = login.login(data[1], Integer.parseInt(data[2]));
                        
                        //This probably needs to be demanded to a factory method
                        Response dto = new Response("OK", user);
                        
                        out.println(dto);
                        out.flush();
                        break;
                    }
                    case "close": {
                        close();
                        System.out.println(connectedClient + " has disconnected");
                        return;
                    }
                }
            }
        } catch (IOException | RowToModelParseException ex) {
            ex.printStackTrace();
            //Either deal with exception generically here or specifically in each call
            Response dto = new Response(ex.getMessage(), null);
            out.println(dto);
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
            in.close();
            out.close();
            dataIn.close();
            dataOut.close();
            socket.close();
        } catch (NullPointerException ex) {
            System.err.println("Socket is null");
        }
    }
}
