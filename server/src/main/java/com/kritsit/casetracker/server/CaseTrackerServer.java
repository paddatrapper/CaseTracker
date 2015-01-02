package com.kritsit.casetracker.server;

public class CaseTrackerServer {
    private static final String VERSION = "0.1a";

    public CaseTrackerServer(int port) {
        
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            new CaseTrackerServer(1244);
        } else if ("-v".equals(args[0]) || "--version".equals(args[0])) {
            System.out.println("Version: " + getVersion());
            System.exit(0);
        } else {
            try {
                String arg = args[0].trim().replace("-", "");
                int port = Integer.parseInt(arg);
                new CaseTrackerServer(port);
            } catch (NumberFormatException ex) {
                System.err.println("Arguments incorrect. Port is required.");
                System.exit(1);
            }
        }
    }

    public static String getVersion() {
        return VERSION;
    }
}
