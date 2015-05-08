package com.kritsit.casetracker.shared.domain.model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Staff implements Externalizable {
    private static final long serialVersionUID = 10L;
    private StringProperty usernameProperty;
    private StringProperty firstNameProperty;
    private StringProperty lastNameProperty;
    private StringProperty departmentProperty;
    private StringProperty positionProperty;
    private ObjectProperty<Permission> permissionProperty;

    public Staff() {
        usernameProperty = new SimpleStringProperty();
        firstNameProperty = new SimpleStringProperty();
        lastNameProperty = new SimpleStringProperty();
        departmentProperty = new SimpleStringProperty();
        positionProperty = new SimpleStringProperty();
        permissionProperty = new SimpleObjectProperty<>();
    }

    public Staff(String username, String firstName, String lastName, String department, String position, Permission permission) {
        usernameProperty = new SimpleStringProperty(username);
        firstNameProperty = new SimpleStringProperty(firstName);
        lastNameProperty = new SimpleStringProperty(lastName);
        departmentProperty = new SimpleStringProperty(department);
        positionProperty = new SimpleStringProperty(position);
        permissionProperty = new SimpleObjectProperty<>(permission);
    }

    public String getUsername() {
        return usernameProperty.get();
    }

    public String getFirstName() {
        return firstNameProperty.get();
    }

    public String getLastName() {
        return lastNameProperty.get();
    }

    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    public String getDepartment() {
        return departmentProperty.get();
    }

    public String getPosition() {
        return positionProperty.get();
    }

    public Permission getPermission() {
        return permissionProperty.get();
    }

    public Property nameProperty() {
        return new SimpleStringProperty(getName());
    }

    public void setUsername(String username) {
        usernameProperty.set(username);
    }

    public void setFirstName(String firstName) {
        firstNameProperty.set(firstName);
    }

    public void setLastName(String lastName) {
        lastNameProperty.set(lastName);
    }

    public void setDepartment(String department) {
        departmentProperty.set(department);
    }

    public void setPosition(String position) {
        positionProperty.set(position);
    }

    public void setPermission(Permission permission) {
        permissionProperty.set(permission);
    }

    @Override
    public int hashCode() {
        return ((getUsername() + getFirstName() + getLastName() + getDepartment() + getPosition()).hashCode() + getPermission().hashCode()) / 3;
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
        String result = "Staff: ";
        result += getUsername() + " ";
        result += "(" + getName() + ")";
        return result;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getUsername());
        out.writeObject(getFirstName());
        out.writeObject(getLastName());
        out.writeObject(getDepartment());
        out.writeObject(getPosition());
        out.writeObject(getPermission());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setUsername((String) in.readObject());
        setFirstName((String) in.readObject());
        setLastName((String) in.readObject());
        setDepartment((String) in.readObject());
        setPosition((String) in.readObject());
        setPermission((Permission) in.readObject());
    }
}
