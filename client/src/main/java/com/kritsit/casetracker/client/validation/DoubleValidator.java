package com.kritsit.casetracker.client.validation;

public class DoubleValidator implements IValidator<Double> {
    public boolean validate(Object obj) {
        if (obj == null) {
            return false;
        }
        String s = obj.toString();
        try {
            double value = Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
