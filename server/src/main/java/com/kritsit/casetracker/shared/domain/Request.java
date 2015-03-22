package com.kritsit.casetracker.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Request implements Serializable {
    private static final long serialVersionUID = 10L;
    private String command;
    private List arguments;

    public Request(String command, List arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public Request(String command) {
        this(command, new ArrayList<String>());
    }

    public String getCommand() {
       return command;
    }

   public List getArguments() {
      return arguments;
   }
} 
