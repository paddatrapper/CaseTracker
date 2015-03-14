package com.kritsit.casetracker.shared.domain;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 10L;
    private String command;
    private String[] arguments;

    public Request(String command, String[] arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public Request(String command, String argument) {
        this(command, new String[]{argument});
    }

    public Request(String command) {
        this(command, new String[0]);
    }

    public String getCommand() {
       return command;
    }

   public String[] getArguments() {
      return arguments;
   }
} 
