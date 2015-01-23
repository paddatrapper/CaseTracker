package com.kritsit.casetracker.client.domain.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Defendant {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String telephoneNumber;
    private String emailAddress;
    //private List<Vehicle> vehicles;
    private boolean secondOffence;

    public Defendant(String id, String firstName, String lastName, String address, String telephoneNumber, String emailAddress, boolean secondOffence) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.secondOffence = secondOffence;

    //    vehicles = new ArrayList<>();
    }
/*
    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public void addVehicles(Vehicle[] vehicle) {
        if (vehicle != null) {
            for (Vehicle v : vehicle) {
                addVehicle(v);
            }
        }
    }
    */

    // Accessor methods:
    public String getId() {
        return id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    /*
    public List<Vehicle> getVehicle() {
        return vehicle;
    }
    */

    public boolean isSecondOffence() {
        return secondOffence;
    }

    // Mutator methods:
    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /*
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    */

    public void setSecondOffence(boolean secondOffence) {
        this.secondOffence = secondOffence;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Defendant other = (Defendant) obj;
        return Objects.equals(this.id, other.id);
    }
}
