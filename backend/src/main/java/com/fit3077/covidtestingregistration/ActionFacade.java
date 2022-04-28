package com.fit3077.covidtestingregistration;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.booking.BookingStatus;
import com.fit3077.covidtestingregistration.login.Login;
import com.fit3077.covidtestingregistration.user.User;

public class ActionFacade {

    private User user;
    private boolean isBookingSuccess;

    public User getUser() {
        return this.user;
    }

    public boolean getIsBookingSuccess() {
        return this.isBookingSuccess;
    }

    public void createLogin(String userName, String password) {
        Login login = new Login(userName, password);
        this.user = login.loginUser();
    }

    public void createBooking(ObjectNode userObject) {
        this.isBookingSuccess = this.user.handleBooking(userObject);
    }

    public String checkPinCode(String smsPin) {
        BookingApi bookingApi = new BookingApi();
        for (ObjectNode bookingNode : bookingApi.getBookings()) {
            if (bookingNode.get("smsPin").textValue().equals(smsPin)) {
                return bookingNode.get("status").textValue();
            }
        }
        return BookingStatus.INVALID.toString();
    }
}
