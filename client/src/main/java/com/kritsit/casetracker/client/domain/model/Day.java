package com.kritsit.casetracker.client.domain.model;

import javafx.beans.property.Property;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.util.ArrayList;
import java.util.List;

public class Day {
    private ObjectProperty<String> dayNumberProperty;
    private ObjectProperty<List<Appointment>> appointmentsProperty;

    public Day() {
        dayNumberProperty = new SimpleObjectProperty<>("");
        appointmentsProperty = new SimpleObjectProperty<List<Appointment>>(new ArrayList<Appointment>());
    }

    public Day(int dayNumber) {
        dayNumberProperty = new SimpleObjectProperty<>(String.valueOf(dayNumber));
        appointmentsProperty = new SimpleObjectProperty<List<Appointment>>(new ArrayList<Appointment>());
    }

    public Day(int dayNumber, List<Appointment> appointments) {
        dayNumberProperty = new SimpleObjectProperty<>(String.valueOf(dayNumber));
        appointmentsProperty = new SimpleObjectProperty<>(appointments);
    }

    public void addAppointment(Appointment appointment) {
        getAppointments().add(appointment);
    }

    public String getNumber() {
        return dayNumberProperty.get();
    }

    public List<Appointment> getAppointments() {
        return appointmentsProperty.get();
    }

    public void setNumber(String number) {
        dayNumberProperty.set(number);
    }

    public void setAppointments(List<Appointment> appointments) {
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
        return (getNumber().hashCode() + getAppointments().hashCode())  / 3;
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
        StringBuilder result = new StringBuilder();
        result.append(getNumber());
        result.append("\n");
        for (Appointment appointment : getAppointments()) {
            result.append(appointment.getDetails());
            result.append("\n");
        }
        return result.toString();
    }
}
