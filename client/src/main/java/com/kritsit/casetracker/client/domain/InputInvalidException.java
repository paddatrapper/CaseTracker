package com.kritsit.casetracker.client.domain;

import java.io.IOException;

public class InputInvalidException extends IOException {
    public InputInvalidException(String message) {
        super(message);
    }
    
    @Override
    public String getLocalizedMessage() {
        return "Input was invalid - " + getMessage();
    }
}
