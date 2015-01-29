package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.server.domain.Domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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
            connection = DriverManager.getConnection("jdbc:mysql://" + host 
                    + ":" + port + "/" + schema, username, password);
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

    private ResultSet get(String sql) {
        ResultSet query = null;
        try {
            Statement statement = connection.createStatement(ResultSet.CONCUR_READ_ONLY, 
                                                             ResultSet.TYPE_FORWARD_ONLY);
            query = statement.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return query;
    }
    
    public Map<String, String> executeQuery(String sql){
    	ResultSet rs = get(sql);
        if (isEmpty(rs)) {
            return null;
        }
        Map<String, String> details = new HashMap<>();
        try {
            ResultSetMetaData meta = rs.getMetaData();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String column = meta.getColumnName(i);
                details.put(column, rs.getString(column));
            }
            return details;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }



    private boolean isEmpty(ResultSet rs) {
        try {
            return rs == null || !rs.first();
        } catch (SQLException ex) {
            return true;
        }
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
