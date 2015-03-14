package com.kritsit.casetracker.shared.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Defendant extends Person{
    private boolean secondOffence;
    private List<Vehicle> vehicles;

    public Defendant(String id, String firstName, String lastName, String address, String telephoneNumber, String emailAddress, boolean secondOffence) {
        super(id, firstName, lastName, address, telephoneNumber, emailAddress);
        this.secondOffence = secondOffence;
        vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public void addVehicles(Vehicle[] vehicle) {
        for (Vehicle v : vehicle) {
            addVehicle(v);
        }
    }

    // Accessor methods:
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public boolean isSecondOffence() {
        return secondOffence;
    }

    // Mutator methods:
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void setSecondOffence(boolean secondOffence) {
        this.secondOffence = secondOffence;
    }

    @Override
    public int hashCode() {
        Boolean so = Boolean.valueOf(secondOffence);
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
        String result = "Defendant: ";
        result += getName() + " ";
        result += "(" + getId() + ")";
        return result;
    }
}
