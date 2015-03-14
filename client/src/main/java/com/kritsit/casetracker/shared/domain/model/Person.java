package com.kritsit.casetracker.shared.domain.model;

import java.util.Objects;

public class Person {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String telephoneNumber;
    private String emailAddress;

    public Person(String id, String firstName, String lastName, String address, String telephoneNumber, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
    }

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

    @Override
    public int hashCode() {
        return (id + firstName + lastName + address + telephoneNumber + emailAddress).hashCode() / 3;
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
        String result = "Person: ";
        result += getName() + " ";
        result += "(" + id + ")";
        return result;
    }
}
