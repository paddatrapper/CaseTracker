package com.kritsit.casetracker.shared.domain.model;

import javafx.beans.property.Property;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public class Incident implements Externalizable {
    private static final long serialVersionUID = 10L;
    private IntegerProperty indexIdProperty;
    private StringProperty addressProperty;
    private DoubleProperty longitudeProperty;
    private DoubleProperty latitudeProperty;
    private StringProperty regionProperty;
    private ObjectProperty<LocalDate> dateProperty;
    private ObjectProperty<LocalDate> followUpDateProperty;
    private BooleanProperty followedUpProperty;

    public Incident() {
        indexIdProperty = new SimpleIntegerProperty();
        addressProperty = new SimpleStringProperty();
        regionProperty = new SimpleStringProperty();
        dateProperty = new SimpleObjectProperty<>();
        followUpDateProperty = new SimpleObjectProperty<>();
        followedUpProperty = new SimpleBooleanProperty();
        longitudeProperty = new SimpleDoubleProperty();
        latitudeProperty = new SimpleDoubleProperty();
    }

    public Incident(int indexId, String address, String region, LocalDate date, 
            LocalDate followUpDate, boolean followedUp) {
        indexIdProperty = new SimpleIntegerProperty(indexId);
        addressProperty = new SimpleStringProperty(address);
        regionProperty = new SimpleStringProperty(region);
        dateProperty = new SimpleObjectProperty<>(date);
        followUpDateProperty = new SimpleObjectProperty<>(followUpDate);
        followedUpProperty = new SimpleBooleanProperty(followedUp);
        longitudeProperty = new SimpleDoubleProperty();
        latitudeProperty = new SimpleDoubleProperty();
    }

    public Incident(int indexId, double longitude, double latitude, String region, 
            LocalDate date, LocalDate followUpDate, boolean followedUp) {
        indexIdProperty = new SimpleIntegerProperty(indexId);
        longitudeProperty = new SimpleDoubleProperty(longitude);
        latitudeProperty = new SimpleDoubleProperty(latitude);
        regionProperty = new SimpleStringProperty(region);
        dateProperty = new SimpleObjectProperty<>(date);
        followUpDateProperty = new SimpleObjectProperty<>(followUpDate);
        followedUpProperty = new SimpleBooleanProperty(followedUp);
        addressProperty = new SimpleStringProperty();
    }

    public int getIndexId() {
        return indexIdProperty.get();
    }

    public double getLatitude() {
        return latitudeProperty.get();
    }

    public double getLongitude() {
        return longitudeProperty.get();
    }

    public String getAddress() {
        return addressProperty.get();
    }

    public String getRegion() {
        return regionProperty.get();
    }

    public LocalDate getDate() {
        return dateProperty.get();
    }

    public LocalDate getFollowUpDate() {
        return followUpDateProperty.get();
    }

    public Property dateProperty() {
        return dateProperty;
    }

    public boolean isFollowedUp() {
        return followedUpProperty.get();
    }

    public void setIndexId(int indexId) {
        indexIdProperty.set(indexId);
    }

    public void setLongitude(double longitude) {
        longitudeProperty.set(longitude);
    }

    public void setLatitude(double latitude) {
        latitudeProperty.set(latitude);
    }

    public void setAddress(String address) {
        addressProperty.set(address);
    }

    public void setRegion(String region) {
        regionProperty.set(region);
    }

    public void setDate(LocalDate date) {
        dateProperty.set(date);
    }

    public void setFollowUpDate(LocalDate followUpDate) {
        followUpDateProperty.set(followUpDate);
    }

    public void setFollowedUp(boolean followedUp) {
        followedUpProperty.set(followedUp);
    }

    public static LocalDate getDefaultFollowUpDate(LocalDate date) {
        return date.plusWeeks(1L);
    }

    @Override
    public int hashCode() {
        Boolean fu = Boolean.valueOf(isFollowedUp());
        return ((getAddress() + getLongitude() + getLatitude() + getRegion()).hashCode() + getDate().hashCode() + getFollowUpDate().hashCode() + fu.hashCode()) / 3;
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
        String result = "Incident: ";
        if (getAddress() != null && getAddress().length() != 0) {
            result += getAddress() + " ";
        } else {
            result += getLongitude() + ", ";
            result += getLatitude() + " ";
        }
        result += "(" + getDate().toString() + ")";
        return result;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
       out.writeObject(getAddress()); 
       out.writeDouble(getLongitude());
       out.writeDouble(getLatitude());
       out.writeObject(getRegion());
       out.writeObject(getDate());
       out.writeObject(getFollowUpDate());
       out.writeBoolean(isFollowedUp());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setAddress((String) in.readObject());
        setLongitude(in.readDouble());
        setLatitude(in.readDouble());
        setRegion((String) in.readObject());
        setDate((LocalDate) in.readObject());
        setFollowUpDate((LocalDate) in.readObject());
        setFollowedUp(in.readBoolean());
    }
}
