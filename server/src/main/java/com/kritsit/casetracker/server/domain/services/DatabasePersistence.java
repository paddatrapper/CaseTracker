package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.server.domain.Domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabasePersistence implements IPersistenceService {
    private boolean connected;
    private Connection connection;

    public DatabasePersistence() {
        connected = false;
    }

    public boolean open() {
        String host = Domain.getDbHostName();
        int port = Domain.getDbPort();
        String schema = Domain.getDbSchema();
        String username = Domain.getDbUsername();
        String password = Domain.getDbPassword();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql//" + host + ":" + schema, username, password);
            connected = true;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            connected = false;
        }
        return connected;
    }

    public boolean isOpen() {
        return connected;
    }

    public void close() {
        try {
            connection.close();
            connected = false;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {}
    }
}
