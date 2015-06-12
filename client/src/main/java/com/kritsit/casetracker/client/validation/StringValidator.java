package com.kritsit.casetracker.client.validation;

public class StringValidator implements IValidator<String> {
    public boolean validate(String obj) {
        if (obj == null || obj.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
