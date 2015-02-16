package com.kritsit.casetracker.client.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ObjectArrayValidator<T> implements IArrayValidator<T> {
    private final Logger logger = LoggerFactory.getLogger(ObjectArrayValidator.class);

    public ObjectArrayValidator() {}

    public boolean contains(Object obj, T[] array) {
        if (array == null || obj == null) {
            logger.debug("Array or object not defined");
            return false;
        }
        if (array.length == 0) {
            logger.debug("Array is empty");
            return false;
        }
        for (T i : array) {
            if (i.equals(obj)) {
                logger.debug("Array contains {}", obj.toString());
                return true;
            }
        }
        logger.debug("Array does not contain {}", obj.toString());
        return false;
    }

    public boolean isEmpty(T[] obj) {
        if (obj == null) {
            logger.debug("Array not defined");
            return false;
        }
        return obj.length == 0;
    }

    public boolean isNull(T[] obj) {
        return obj == null;
    }
}
