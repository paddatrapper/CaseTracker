package com.kritsit.casetracker.client.validation;

import com.kritsit.casetracker.client.domain.model.Period;

import java.time.DateTimeException;
import java.time.LocalDate;

public class DateValidator implements IValidator<LocalDate> {
    private LocalDate checkDate;
    private Period period;

    public DateValidator(LocalDate checkDate, Period period) {
        this.checkDate = checkDate;
        this.period = period;
    }

    public boolean validate(LocalDate obj) {
        if (obj == null) {
            return false;
        }
        switch (period) {
            case BEFORE :
                return obj.isBefore(checkDate);
            case EQUALS :
                return obj.isEqual(checkDate);
            case AFTER :
                return obj.isAfter(checkDate);
            default :
                return false;
        }
    }
}
