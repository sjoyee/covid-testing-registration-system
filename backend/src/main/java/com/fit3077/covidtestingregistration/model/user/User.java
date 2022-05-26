package com.fit3077.covidtestingregistration.model.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.model.observer.BookingEventManager;

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
        this.isReceptionist = false;
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

    public abstract boolean handleBooking(ObjectNode objectNode, BookingEventManager bookingEvents);

    public abstract boolean updateData(String code,  BookingEventManager bookingEvents);

    public ObjectNode checkPinCode(String smsPin) {
        BookingApi bookingApi = new BookingApi();
        for (ObjectNode bookingNode : bookingApi.getBookings()) {
            if (bookingNode.get("smsPin").textValue().equals(smsPin)) {
                return bookingNode;
            }
        }
        return null;
    }

    public ObjectNode checkBookingId(String bookingId) {
        BookingApi bookingApi = new BookingApi();
        return bookingApi.getBookingById(bookingId);
    }
}
