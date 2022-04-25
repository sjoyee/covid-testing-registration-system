package com.fit3077.covidtestingregistration.user;

public abstract class User {
    private String id;
    private String givenName;
    private String familyName;
    private String phoneNumber;
    private UserType userType;

    protected User(String id, String givenName, String familyName, String phoneNumber) {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return this.id;
    }

    public String getGivenName() {
        return this.givenName;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}
