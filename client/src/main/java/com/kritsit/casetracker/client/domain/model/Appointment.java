package com.kritsit.casetracker.client.domain.model;

import javafx.beans.property.Property;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

public class Appointment {
    ObjectProperty<LocalDate> dateProperty;
    ObjectProperty<String> detailsProperty;

    public Appointment(LocalDate date, String details) {
        dateProperty = new SimpleObjectProperty<>(date);
        detailsProperty = new SimpleObjectProperty<>(details);
    }

    public LocalDate getDate() {
        return dateProperty.get();
    }

    public String getDetails() {
        return detailsProperty.get();
    }

    public void setDate(LocalDate date) {
        dateProperty.set(date);
    }

    public void setDetails(String details) {
        detailsProperty.set(details);
    }

    @Override
    public int hashCode() {
        return (getDate().hashCode() + getDetails().hashCode())  / 3;
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
        String result = getDate().toString() + " - ";
        result += getDetails();
        return result;
    }
}
