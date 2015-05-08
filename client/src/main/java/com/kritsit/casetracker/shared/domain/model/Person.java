package com.kritsit.casetracker.shared.domain.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class Person implements Externalizable {
    private static final long serialVersionUID = 10L;
    private StringProperty idProperty;
    private StringProperty firstNameProperty;
    private StringProperty lastNameProperty;
    private StringProperty addressProperty;
    private StringProperty telephoneNumberProperty;
    private StringProperty emailAddressProperty;

    public Person() {
        idProperty = new SimpleStringProperty();
        firstNameProperty = new SimpleStringProperty();
        lastNameProperty = new SimpleStringProperty();
        addressProperty = new SimpleStringProperty();
        telephoneNumberProperty = new SimpleStringProperty();
        emailAddressProperty = new SimpleStringProperty();
    }

    public Person(String id, String firstName, String lastName, String address, String telephoneNumber, String emailAddress) {
        idProperty = new SimpleStringProperty(id);
        firstNameProperty = new SimpleStringProperty(firstName);
        lastNameProperty = new SimpleStringProperty(lastName);
        addressProperty = new SimpleStringProperty(address);
        telephoneNumberProperty = new SimpleStringProperty(telephoneNumber);
        emailAddressProperty = new SimpleStringProperty(emailAddress);
    }

    // Accessor methods:
    public String getId() {
        return idProperty.get();
    }

    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    public String getFirstName() {
        return firstNameProperty.get();
    }

    public String getLastName() {
        return lastNameProperty.get();
    }

    public String getAddress() {
        return addressProperty.get();
    }

    public String getTelephoneNumber() {
        return telephoneNumberProperty.get();
    }

    public String getEmailAddress() {
        return emailAddressProperty.get();
    }

    // Mutator methods:
    public void setId(String id) {
        idProperty.set(id);
    }

    public void setFirstName(String firstName) {
        firstNameProperty.set(firstName);
    }

    public void setLastName(String lastName) {
        lastNameProperty.set(lastName);
    }

    public void setAddress(String address) {
        addressProperty.set(address);
    }

    public void setTelephoneNumber(String telephoneNumber) {
        telephoneNumberProperty.set(telephoneNumber);
    }

    public void setEmailAddress(String emailAddress) {
        emailAddressProperty.set(emailAddress);
    }

    @Override
    public int hashCode() {
        return (getId() + getFirstName() + getLastName() + getAddress() + getTelephoneNumber() + getEmailAddress()).hashCode() / 3;
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
        result += "(" + getId() + ")";
        return result;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getId());
        out.writeObject(getFirstName());
        out.writeObject(getLastName());
        out.writeObject(getAddress());
        out.writeObject(getTelephoneNumber());
        out.writeObject(getEmailAddress());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setId((String) in.readObject());
        setFirstName((String) in.readObject());
        setLastName((String) in.readObject());
        setAddress((String) in.readObject());
        setTelephoneNumber((String) in.readObject());
        setEmailAddress((String) in.readObject());
    }
}
