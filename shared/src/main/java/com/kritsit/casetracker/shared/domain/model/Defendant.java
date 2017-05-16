package com.kritsit.casetracker.shared.domain.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ObjectProperty;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

public class Defendant extends Person {
    private BooleanProperty secondOffenceProperty;
    private ObjectProperty<List<Vehicle>> vehicleProperty;

    public Defendant() {
        super();
        secondOffenceProperty = new SimpleBooleanProperty();
        vehicleProperty = new SimpleObjectProperty<List<Vehicle>>(new ArrayList<Vehicle>());
    }

    public Defendant(int indexId, String id, String firstName, String lastName, 
            String address, String telephoneNumber, String emailAddress, 
            boolean secondOffence) {
        super(indexId, id, firstName, lastName, address, telephoneNumber, emailAddress);
        secondOffenceProperty = new SimpleBooleanProperty(secondOffence);
        vehicleProperty = new SimpleObjectProperty<List<Vehicle>>(new ArrayList<Vehicle>());
    }

    public void addVehicle(Vehicle vehicle) {
        getVehicles().add(vehicle);
    }

    public void addVehicles(Vehicle[] vehicle) {
        for (Vehicle v : vehicle) {
            addVehicle(v);
        }
    }

    // Accessor methods:
    public List<Vehicle> getVehicles() {
        return vehicleProperty.get();
    }

    public boolean isSecondOffence() {
        return secondOffenceProperty.get();
    }

    // Mutator methods:
    public void setVehicles(List<Vehicle> vehicles) {
        vehicleProperty.set(vehicles);
    }

    public void setSecondOffence(boolean secondOffence) {
        secondOffenceProperty.set(secondOffence);
    }

    @Override
    public int hashCode() {
        Boolean so = Boolean.valueOf(isSecondOffence());
        return super.hashCode() + (so.hashCode() / 3);
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
        String result = getName();
        return result;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeBoolean(isSecondOffence());
        out.writeObject(getVehicles());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        setSecondOffence(in.readBoolean());
        setVehicles((List<Vehicle>) in.readObject());
    }
}
