package com.kritsit.casetracker.client.domain.services;

public interface ILoginService {
    boolean login(String name, char[] password);
}
