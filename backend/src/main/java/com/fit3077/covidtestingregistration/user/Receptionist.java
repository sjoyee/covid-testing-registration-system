package com.fit3077.covidtestingregistration.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.api.UserApi;
import com.fit3077.covidtestingregistration.booking.Booking;
import com.fit3077.covidtestingregistration.booking.BookingStatus;

public class Receptionist extends User {

    private String testingSiteId;

    protected Receptionist(String id, String givenName, String familyName, String userName, String phoneNumber,
            String testingSiteId) {
        super(id, givenName, familyName, userName, phoneNumber);
        this.testingSiteId = testingSiteId;
        setIsReceptionist(true);
    }

    @Override
    public boolean handleBooking(ObjectNode userObject) {
        String customerId = "";
        String userName = userObject.get("userName").textValue();
        UserApi userApi = new UserApi();

        for (ObjectNode userNode : new UserApi().getUsers()) {
            if (userNode.get("userName").textValue().equals(userName)) {
                customerId = userNode.get("id").textValue();
                break;
            }
        }
        if (customerId.isEmpty()) {
            userObject.put("isCustomer", true);
            userObject.put("isAdmin", false);
            userObject.put("isHealthcareWorker", false);

            customerId = userApi.createNewUser(userObject).get("id").textValue();
        }
        Booking booking = new Booking(customerId, this.testingSiteId);
        return booking.getSuccess();
    }

    public String checkStatus(String smsPin) {
        BookingApi bookingApi = new BookingApi();
        for (ObjectNode bookingNode : bookingApi.getBookings()) {
            if (bookingNode.get("smsPin").textValue().equals(smsPin)) {
                return bookingNode.get("status").textValue();
            }
        }
        return BookingStatus.INVALID.toString();
    }
}
