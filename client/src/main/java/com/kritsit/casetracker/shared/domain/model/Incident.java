package com.kritsit.casetracker.shared.domain.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Incident implements Serializable {
    private static final long serialVersionUID = 10L;
    private String address;
    private double longitude;
    private double latitude;
    private String region;
    private Date date;
    private Date followUpDate;
    private boolean followedUp;

    public Incident(String address, String region, Date date, Date followUpDate, boolean followedUp) {
        this.address = address;
        this.region = region;
        this.date = date;
        this.followUpDate = followUpDate;
        this.followedUp = followedUp;
    }

    public Incident(double longitude, double latitude, String region, Date date, Date followUpDate, boolean followedUp) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.region = region;
        this.date = date;
        this.followUpDate = followUpDate;
        this.followedUp = followedUp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getRegion() {
        return region;
    }

    public Date getDate() {
        return date;
    }

    public Date getFollowUpDate() {
        return followUpDate;
    }

    public boolean isFollowedUp() {
        return followedUp;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFollowUpDate(Date followUpDate) {
        this.followUpDate = followUpDate;
    }

    public void setFollowedUp(boolean followedUp) {
        this.followedUp = followedUp;
    }

    public Date getDefaultFollowUpDate() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(GregorianCalendar.DATE, 7);
        return cal.getTime();
    }

    @Override
    public int hashCode() {
        Boolean fu = Boolean.valueOf(followedUp);
        return ((address + region).hashCode() + date.hashCode() + followUpDate.hashCode() + fu.hashCode()) / 3;
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String result = "Incident: ";
        result += address + " ";
        result += "(" + df.format(date) + ")";
        return result;
    }
}
