package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class User {
    private String id;
    private String givenName;
    private String familyName;
    private String userName;
    private String phoneNumber;
    private boolean isCustomer;
    private boolean isReceptionist;
    private boolean isHealthcareWorker;

    protected User(String id, String givenName, String familyName, String userName, String phoneNumber) {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.isCustomer = false;
        this.isReceptionist = false; // or receptionist
        this.isHealthcareWorker = false;
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

    public String getUserName() {
        return this.userName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public boolean getIsCustomer() {
        return this.isCustomer;
    }

    public boolean getIsReceptionist() {
        return this.isReceptionist;
    }

    public boolean getIsHealthcareWorker() {
        return this.isHealthcareWorker;
    }

    public void setIsCustomer(boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    public void setIsReceptionist(boolean isReceptionist) {
        this.isReceptionist = isReceptionist;
    }

    public void setIsHealthcareWorker(boolean isHealthcareWorker) {
        this.isHealthcareWorker = isHealthcareWorker;
    }

    public abstract boolean handleBooking(ObjectNode objectNode);

    public abstract boolean updateData(String code);

}
