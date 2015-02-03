package com.kritsit.casetracker.server.datalayer;

import java.sql.SQLException;
import java.util.Map;

public interface IPersistenceService {
    boolean open();
    boolean isOpen();
    Map<String, String> executeQuery(String sql) throws SQLException;
    void close();
}
