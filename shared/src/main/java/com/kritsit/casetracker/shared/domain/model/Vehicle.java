package com.kritsit.casetracker.shared.domain.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Vehicle implements Externalizable {
    private static final long serialVersionUID = 10L;
    private StringProperty registrationProperty;
    private StringProperty makeProperty;
    private StringProperty colourProperty;
    private BooleanProperty isTrailerProperty;

    public Vehicle() {
        registrationProperty = new SimpleStringProperty();
        makeProperty = new SimpleStringProperty();
        colourProperty = new SimpleStringProperty();
        isTrailerProperty = new SimpleBooleanProperty();
    }

    public Vehicle(String registration, String make, String colour, boolean isTrailer) {
        registrationProperty = new SimpleStringProperty(registration);
        makeProperty = new SimpleStringProperty(make);
        colourProperty = new SimpleStringProperty(colour);
        isTrailerProperty = new SimpleBooleanProperty(isTrailer);
    }

    // Accessor methods:
    public String getRegistration() {
        return registrationProperty.get();
    }

    public String getMake() {
        return makeProperty.get();
    }

    public String getColour() {
        return colourProperty.get();
    }

    public boolean isTrailer() {
        return isTrailerProperty.get();
    }

    // Mutator methods:
    public void setRegistration(String registration) {
        registrationProperty.set(registration);
    }

    public void setMake(String make) {
        makeProperty.set(make);
    }

    public void setColour(String colour) {
        colourProperty.set(colour);
    }

    public void setTrailer(boolean isTrailer) {
        isTrailerProperty.set(isTrailer);
    }

    @Override
    public int hashCode() {
        Boolean t = Boolean.valueOf(isTrailer());
        return ((getRegistration() + getMake() + getColour()).hashCode() + t.hashCode()) / 3;
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
        String result = "Vehicle: ";
        result += getColour() + " ";
        result += getMake() + " ";
        result += "(" + getRegistration() + ")";
        return result;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getRegistration());
        out.writeObject(getMake());
        out.writeObject(getColour());
        out.writeBoolean(isTrailer());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setRegistration((String) in.readObject());
        setMake((String) in.readObject());
        setColour((String) in.readObject());
        setTrailer(in.readBoolean());
    }
}
