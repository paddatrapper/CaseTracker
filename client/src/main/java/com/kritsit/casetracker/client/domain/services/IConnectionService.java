package com.kritsit.casetracker.client.domain.services;

import java.io.IOException;

public interface IConnectionService {
    boolean open(String host, int port) throws IllegalArgumentException;
    void close(int status) throws IOException;
    boolean login(String username, int hash);
}
