package com.kritsit.casetracker.client.validation;

class ArrayValidator<T> implements IArrayValidator<T> {

    public ArrayValidator() {}

    public boolean contains(Object obj, T[] array) {
        if (array == null || obj == null) {
            return false;
        }
        if (array.length == 0) {
            return false;
        }
        for (T i : array) {
            if (i.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty(T[] obj) {
        if (obj == null) {
            return false;
        }
        return obj.length == 0;
    }

    public boolean isNull(T[] obj) {
       return false; 

    }
}
