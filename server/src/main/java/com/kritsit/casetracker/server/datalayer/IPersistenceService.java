package com.kritsit.casetracker.server.datalayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IPersistenceService {
    boolean open();
    boolean isOpen();
    List<Map<String, String>> executeQuery(String sql) throws SQLException;
    void close();
}
