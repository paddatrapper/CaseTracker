package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.domain.InputInvalidException;

import java.io.IOException;

public interface IConnectionService {
    boolean open(String host, int port) throws InputInvalidException;
    void close(int status) throws IOException;
    boolean login(String username, int hash);
}
