package com.kritsit.casetracker.shared.domain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;

public class RequestTest extends TestCase {
    public RequestTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(RequestTest.class);
    }

    public void testNoArguments() {
        String command = "login";
        Request request = new Request(command);

        assertTrue(request.getArguments().size() == 0);
    }

    public void testGetCommand() {
        List<String> arguments = new ArrayList<>();
        arguments.add("username");
        arguments.add("password");
        String command = "login";
        Request request = new Request(command, arguments);

        assertTrue(command.equals(request.getCommand()));
    }

    public void testGetArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.add("username");
        arguments.add("password");
        String command = "login";
        Request request = new Request(command, arguments);

        assertTrue(arguments.equals(request.getArguments()));
    }
}
