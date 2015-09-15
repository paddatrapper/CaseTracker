package com.kritsit.casetracker.client.validation;

public class EmailValidator implements IValidator<String> {
    public boolean validate(Object obj) {
        if (obj == null || obj.getClass() != String.class) {
            return false;
        }
        String stringObj = (String) obj;
        return isEmailAddress(stringObj);
    }

    public boolean isEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.trim().isEmpty()) {
            return false;
        }
        boolean containsAt = emailAddress.contains("@");
        int dot = emailAddress.lastIndexOf(".");
        int at = emailAddress.indexOf("@");
        boolean contailsDot = dot != -1 && at != -1;
        boolean correctFormat = at != 0 && at < dot && 
            dot != emailAddress.length() - 1;
        return containsAt && contailsDot && correctFormat;
    }
}
