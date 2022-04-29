package com.fit3077.covidtestingregistration;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.booking.Booking;
import com.fit3077.covidtestingregistration.booking.BookingStatus;
import com.fit3077.covidtestingregistration.login.Login;
import com.fit3077.covidtestingregistration.user.User;

public class ActionFacade {

    private User user;

    public User getUser() {
        return this.user;
    }

    public void createLogin(String userName, String password) {
        Login login = new Login(userName, password);
        this.user = login.loginUser();
    }

    public boolean createBooking(ObjectNode userObject) {
        boolean isSuccess = this.user.handleBooking(userObject);
        // if (isSuccess && userObject.get("isHomeBooking").asBoolean()){
        // String patientId = userObject.get("patientId").textValue();

        // }
        return isSuccess;
    }

    private ObjectNode checkPinCode(String smsPin) {
        BookingApi bookingApi = new BookingApi();
        for (ObjectNode bookingNode : bookingApi.getBookings()) {
            if (bookingNode.get("smsPin").textValue().equals(smsPin)) {
                return bookingNode;
            }
        }
        return null;
    }

    public String checkBookingStatus(String smsPin) {
        ObjectNode bookingNode = checkPinCode(smsPin);
        if (bookingNode == null) {
            return BookingStatus.INVALID.toString();
        }
        return bookingNode.get("status").textValue();
    }

    public boolean updateTestKitIssued(String qrCode) {
        return this.user.updateData(qrCode);
    }

    public boolean createCovidTest(ObjectNode testObject) {
        // get booking object
        ObjectNode bookingNode = this.checkPinCode(testObject.get("smsPin").textValue());
        if (bookingNode == null) {
            return false;
        }

        String bookingId = bookingNode.get("id").textValue();
        testObject.put("bookingId", bookingId);

        boolean isSuccess = this.user.handleBooking(testObject);
        // update status
        if (isSuccess) {
            this.user.updateData(bookingId);
        }
        return isSuccess;
    }
}
