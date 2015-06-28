package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.server.domain.Domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabasePersistence implements IPersistenceService {
    private final Logger logger = LoggerFactory.getLogger(DatabasePersistence.class);
    private boolean connected;
    private Connection connection;

    public DatabasePersistence() {
        connected = false;
    }

    public boolean open() {
        logger.debug("Retrieving database connection details");
        String host = Domain.getDbHostName();
        int port = Domain.getDbPort();
        String schema = Domain.getDbSchema();
        String username = Domain.getDbUsername();
        String password = Domain.getDbPassword();

        try {
            logger.info("Opening database connection: {}@{}:{}/{}", username, host, port, schema);
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host 
                    + ":" + port + "/" + schema, username, password);
            connected = true;
            logger.debug("Connected to database");
        } catch (SQLException | ClassNotFoundException ex) {
            logger.error("Unable to open database connection: {}", ex);
            connected = false;
        }
        return connected;
    }

    public boolean isOpen() {
        return connected;
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("SQL_PREPARED_STATEMENT_GENERATED_FROM_NONCONSTANT_STRING")
    private ResultSet get(String sql, String... args) throws SQLException {
        logger.info("Executing request {}", sql);
        
        try(PreparedStatement statement = connection.prepareStatement(sql, ResultSet.CONCUR_READ_ONLY, 
                ResultSet.TYPE_FORWARD_ONLY)) {
            
            for(int i = 0; i < args.length; ++i){
                statement.setString(i + 1, args[i]);
            }
            
            ResultSet query = statement.executeQuery();
            return query;
        } catch (SQLException e){
            throw e;
        }
    }
    
    public List<Map<String, String>> executeQuery(String sql, String... args) throws SQLException{
    	try {
	    open();
	    ResultSet rs = get(sql, args);
	    if (isEmpty(rs)) {
                logger.debug("ResultSet empty");
	        return null;
	    }
            List details = new ArrayList<>();
            rs.beforeFirst();
            while (rs.next()) {
                logger.debug("Creating map of ResultSet row");
                Map<String, String> rowDetails = new HashMap<>();
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String column = meta.getColumnName(i);
                    rowDetails.put(column, rs.getString(column));
                }
                details.add(rowDetails);
            }
	    return details;
    	} finally {
    	    close();
    	}
    }

    private boolean isEmpty(ResultSet rs) throws SQLException {
       return rs == null || !rs.first();
    }

    public void close() {
        logger.info("Closing connection to database");
        try {
            connection.close();
            connected = false;
        } catch (SQLException ex) {
            logger.error("Unable to close connection: {}", ex);
        } catch (NullPointerException ex) {
            logger.debug("Connection already closed");
        }
    }

    public void executeUpdate(String sql) throws SQLException {
        logger.info("Inserting changes to database");
        try{
            open();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        finally{
            close();
            logger.info("Database updated");
        }
    }
}
