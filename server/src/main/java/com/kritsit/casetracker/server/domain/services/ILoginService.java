package com.kritsit.casetracker.server.domain.services;

public interface ILoginService {
    boolean login(String name, int passwordHash);
}
