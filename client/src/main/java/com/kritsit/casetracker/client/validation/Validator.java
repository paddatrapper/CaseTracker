package com.kritsit.casetracker.client.validation;

public class Validator<T> implements IValidator<T> {
    final Class<T> classType;

    public Validator(Class<T> classType) {
        this.classType = classType;
    }

    public boolean validate(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj.getClass() == classType;
    }
}
