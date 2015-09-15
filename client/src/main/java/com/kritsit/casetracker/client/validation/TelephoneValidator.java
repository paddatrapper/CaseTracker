package com.kritsit.casetracker.client.validation;

public class TelephoneValidator implements IValidator<String> {
    public boolean validate(Object obj) {
        if (obj == null || obj.getClass() != String.class) {
            return false;
        }
        String stringObj = (String) obj;
        return isTelephoneNumber(stringObj);
    }

    public boolean isTelephoneNumber(String telephoneNumber) {
        if (telephoneNumber == null || telephoneNumber.isEmpty()) {
            return false;
        }
        return telephoneNumber.length() == 10 && telephoneNumber.startsWith("0");
    }
}
