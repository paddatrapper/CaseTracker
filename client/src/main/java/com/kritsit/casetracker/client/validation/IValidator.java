package com.kritsit.casetracker.client.validation;

public interface IValidator<T> {
    boolean validate(Object obj);
}
