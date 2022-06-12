package com.temp.wisatabinus;

import java.io.Serializable;

public class Favourite implements Serializable {
    private Integer userID, campusID;

    public Favourite(Integer userID, Integer campusID) {
        this.userID = userID;
        this.campusID = campusID;
    }

    public Integer getUserID() { return userID; }

    public void setUserID(Integer userID) { this.userID = userID; }

    public Integer getCampusID() { return campusID; }

    public void setCampusID(Integer campusID) { this.campusID = campusID; }
}
