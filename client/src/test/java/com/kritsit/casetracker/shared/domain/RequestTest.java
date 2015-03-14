package com.kritsit.casetracker.shared.domain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;

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

        assertTrue(request.getArguments().length == 0);
    }

    public void testOneArgument() {
        String argument = "name";
        String command = "login";
        Request request = new Request(command, argument);

        assertTrue(argument.equals(request.getArguments()[0]));
    }

    public void testGetCommand() {
        String[] arguments = {"name", "password"};
        String command = "login";
        Request request = new Request(command, arguments);

        assertTrue(command.equals(request.getCommand()));
    }

    public void testGetArguments() {
        String[] arguments = {"name", "password"};
        String command = "login";
        Request request = new Request(command, arguments);

        assertTrue(Arrays.equals(arguments, request.getArguments()));
    }
}
