package com.kritsit.casetracker.client.domain.datastructures;

public class Staff {
    private String username;
    private String firstName;
    private String lastName;
    private String department;
    private String position;
    private Permission permission;

    public Staff(String username, String firstName, String lastName, String department, String position, Permission permission) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.position = position;
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Staff other = (Staff) obj;
        if (!getUsername().equals(other.getUsername())) {
            return false;
        }
        if (!getFirstName().equals(other.getFirstName())) {
            return false;
        }
        if (!getLastName().equals(other.getLastName())) {
            return false;
        }
        if (!getDepartment().equals(other.getDepartment())) {
            return false;
        }
        if (!getPosition().equals(other.getPosition())) {
            return false;
        }
        return getPermission() == other.getPermission();
    }
}
