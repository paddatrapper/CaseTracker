package com.kritsit.casetracker.client.validation;

public interface IArrayValidator<T> {
    boolean contains(Object obj, T[] array);
    boolean isEmpty(T[] obj);
    boolean isNull(T[] obj);
}
