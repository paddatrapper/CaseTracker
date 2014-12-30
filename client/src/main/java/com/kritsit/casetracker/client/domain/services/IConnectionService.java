package com.kritsit.casetracker.client.domain.services;

public interface IConnectionService {
    boolean open(String host, int port);
}
