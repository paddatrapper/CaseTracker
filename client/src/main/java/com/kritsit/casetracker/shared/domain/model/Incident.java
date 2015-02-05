package com.kritsit.casetracker.shared.domain.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Incident {
    private String address;
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Incident other = (Incident) obj;
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.region, other.region)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
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
