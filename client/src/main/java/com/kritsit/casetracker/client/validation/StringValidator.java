package com.kritsit.casetracker.client.validation;

public class StringValidator implements IValidator<String> {
    public boolean validate(Object obj) {
        if (obj == null || obj.getClass() != String.class) {
            return false;
        }
        String stringObj = (String) obj;
        return !stringObj.trim().isEmpty();
    }
}
