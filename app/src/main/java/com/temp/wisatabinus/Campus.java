package com.temp.wisatabinus;

import java.io.Serializable;

public class Campus implements Serializable {
    private Integer campusID;
    private String campusName, campusLocation, campusAddress, campusImage;
    private Double campusLatitude, campusLongitude;

    public Campus(Integer campusID, String campusName, String campusLocation, String campusAddress, Double campusLatitude, Double campusLongitude, String campusImage) {
        this.campusID = campusID;
        this.campusName = campusName;
        this.campusLocation = campusLocation;
        this.campusAddress = campusAddress;
        this.campusLatitude = campusLatitude;
        this.campusLongitude = campusLongitude;
        this.campusImage = campusImage;
    }

    public Integer getCampusID() { return campusID; }

    public void setCampusID(Integer campusID) { this.campusID = campusID; }

    public String getCampusName() { return campusName; }

    public void setCampusName(String campusName) { this.campusName = campusName; }

    public String getCampusLocation() { return campusLocation; }

    public void setCampusLocation(String campusLocation) { this.campusLocation = campusLocation; }

    public String getCampusAddress() { return campusAddress; }

    public void setCampusAddress(String campusAddress) { this.campusAddress = campusAddress; }

    public String getCampusImage() { return campusImage; }

    public void setCampusImage(String campusImage) { this.campusImage = campusImage; }

    public Double getCampusLatitude() { return campusLatitude; }

    public void setCampusLatitude(Double campusLatitude) { this.campusLatitude = campusLatitude; }

    public Double getCampusLongitude() { return campusLongitude; }

    public void setCampusLongitude(Double campusLongitude) { this.campusLongitude = campusLongitude; }
}
