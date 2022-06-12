package com.temp.wisatabinus;

import java.io.Serializable;

public class User implements Serializable {
    private Integer userID;
    private String userEmailAddress, userPhoneNumber, userPassword;

    public User(Integer userID, String userEmailAddress, String userPhoneNumber, String userPassword) {
        this.userID = userID;
        this.userEmailAddress = userEmailAddress;
        this.userPhoneNumber = userPhoneNumber;
        this.userPassword = userPassword;
    }

    public Integer getUserID() { return userID; }

    public void setUserID(Integer userID) { this.userID = userID; }

    public String getUserEmailAddress() { return userEmailAddress; }

    public void setUserEmailAddress(String userEmailAddress) { this.userEmailAddress = userEmailAddress; }

    public String getUserPhoneNumber() { return userPhoneNumber; }

    public void setUserPhoneNumber(String userPhoneNumber) { this.userPhoneNumber = userPhoneNumber; }

    public String getUserPassword() { return userPassword; }

    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
}
