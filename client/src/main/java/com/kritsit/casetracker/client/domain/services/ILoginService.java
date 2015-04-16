package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.shared.domain.model.Staff;

public interface ILoginService {
    boolean login(String name, String password);
    Staff getUser(String name, String password);
}
