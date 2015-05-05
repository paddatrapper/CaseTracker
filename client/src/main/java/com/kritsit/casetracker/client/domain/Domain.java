package com.kritsit.casetracker.client.domain;

import com.kritsit.casetracker.client.domain.services.IConnectionService;
import com.kritsit.casetracker.client.domain.services.ILoginService;
import com.kritsit.casetracker.client.domain.services.ServerConnection;
import com.kritsit.casetracker.client.domain.services.ServerLogin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Domain {
    private static final Logger logger = LoggerFactory.getLogger(Domain.class);
    private static IConnectionService connection;

    public static String getServerAddress() {
        return "localhost";
    }

    public static int getServerConnectionPort() {
        return 1244;
    }
}
