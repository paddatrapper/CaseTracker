package com.kritsit.casetracker.client.domain.model;

import javafx.beans.property.Property;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.util.ArrayList;
import java.util.List;

public class Day {
    private IntegerProperty dayNumberProperty;
    private ObjectProperty<List<String>> appointmentsProperty;

    public Day(int dayNumber) {
        dayNumberProperty = new SimpleIntegerProperty(dayNumber);
        appointmentsProperty = new SimpleObjectProperty<List<String>>(new ArrayList<String>());
    }

    public Day(int dayNumber, List<String> appointments) {
        dayNumberProperty = new SimpleIntegerProperty(dayNumber);
        appointmentsProperty = new SimpleObjectProperty<>(appointments);
    }

    public void addAppointment(String appointment) {
        getAppointments().add(appointment);
    }

    public int getNumber() {
        return dayNumberProperty.get();
    }

    public List<String> getAppointments() {
        return appointmentsProperty.get();
    }

    public void setNumber(int number) {
        dayNumberProperty.set(number);
    }

    public void setAppointments(List<String> appointments) {
        appointmentsProperty.set(appointments);
    }

    public Property dayNumberProperty() {
        return dayNumberProperty;
    }

    public Property appointmentsProperty() {
        return appointmentsProperty;
    }

    @Override
    public int hashCode() {
        Integer dn = Integer.valueOf(getNumber());
        return (dn.hashCode() + getAppointments().hashCode())  / 3;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        String result = getNumber() + "\n";
        for (String appointment : getAppointments()) {
            result += appointment + "\n";
        }
        return result;
    }
}
