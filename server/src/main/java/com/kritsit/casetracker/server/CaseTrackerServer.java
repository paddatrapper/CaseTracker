package com.kritsit.casetracker.server;

import com.kritsit.casetracker.server.domain.services.IListeningService;
import com.kritsit.casetracker.server.domain.services.SocketListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CaseTrackerServer {
    private static final String VERSION = "0.1a";
    private static final Logger logger = LoggerFactory.getLogger(CaseTrackerServer.class);
    private IListeningService listener;

    public CaseTrackerServer() {
        listener = new SocketListener();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            logger.debug("Starting server with default preferences");
            CaseTrackerServer server = new CaseTrackerServer();
            server.listen(1244);
        } else if ("-v".equals(args[0]) || "--version".equals(args[0])) {
            logger.debug("Printing version information");
            System.out.println("Version: " + getVersion());
            System.exit(0);
        } else {
            try {
                String arg = args[0].trim().replace("-", "");
                int port = Integer.parseInt(arg);
                CaseTrackerServer server = new CaseTrackerServer();
                server.listen(port);
            } catch (NumberFormatException ex) {
                logger.error("Arguments invalid. Port is required");
                System.exit(1);
            }
        }
    }

    public static String getVersion() {
        return VERSION;
    }

    private void listen(int port) {
        try {
            listener.listen(port);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(2);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    public void close() {
        try {
            listener.stop();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
