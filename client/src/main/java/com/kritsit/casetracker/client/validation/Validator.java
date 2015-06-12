package com.kritsit.casetracker.client.validation;

public class Validator<T> implements IValidator<T> {
    public boolean validate(T obj) {
        return obj != null;
    }
}
