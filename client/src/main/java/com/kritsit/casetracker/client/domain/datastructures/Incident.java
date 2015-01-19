package com.kritsit.casetracker.client.domain.datastructures;

import java.util.Date;

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
}
