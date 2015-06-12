package com.kritsit.casetracker.client.validation;

public class BooleanValidator implements IValidator<Boolean> {
    public boolean validate(Boolean obj) {
        return obj != null;
    }
}
