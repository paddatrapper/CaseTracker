package com.kritsit.casetracker.client.validation;

import com.kritsit.casetracker.client.domain.model.Period;

import java.time.DateTimeException;
import java.time.LocalDate;

public class DateValidator implements IValidator<LocalDate> {
    private LocalDate checkDate;
    private Period period;

    public DateValidator() {
        this.period = Period.NONE;
    }

    public DateValidator(Period period, LocalDate checkDate) {
        this.checkDate = checkDate;
        this.period = period;
    }

    public boolean validate(Object obj) {
        if (obj == null || obj.getClass() != LocalDate.class) {
            return false;
        }
        LocalDate date = (LocalDate) obj;
        switch (period) {
            case BEFORE :
                return date.isBefore(checkDate);
            case EQUALS :
                return date.isEqual(checkDate);
            case AFTER :
                return date.isAfter(checkDate);
            default :
                return true;
        }
    }
}
