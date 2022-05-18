package com.fit3077.covidtestingregistration.model.booking;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.user.UserGenerator;

public class BookingFacade {
    public boolean createBooking(String userId, ObjectNode userObject) {
        return new UserGenerator().generateUser(userId).handleBooking(userObject);
    }

    public String checkStatus(String userId, String smsPin) {
        ObjectNode bookingNode = new UserGenerator().generateUser(userId).checkPinCode(smsPin);
        if (bookingNode == null) {
            return BookingStatus.INVALID.toString();
        }
        return bookingNode.get("status").textValue();
    }

    public boolean updateHomeTestKit(String userId, String qrCode) {
        return new UserGenerator().generateUser(userId).updateData(qrCode);
    }
}
