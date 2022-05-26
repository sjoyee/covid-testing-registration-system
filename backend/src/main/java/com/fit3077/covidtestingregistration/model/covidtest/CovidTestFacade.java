package com.fit3077.covidtestingregistration.model.covidtest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.booking.observer.BookingEventManager;
import com.fit3077.covidtestingregistration.model.user.User;
import com.fit3077.covidtestingregistration.model.user.UserGenerator;

public class CovidTestFacade {
    public boolean createCovidTest(String userId, ObjectNode testObject,BookingEventManager bookingEvents) {
        // get booking object
        User user = new UserGenerator().generateUser(userId,bookingEvents);
        ObjectNode bookingNode = user.checkPinCode(testObject.get("smsPin").textValue());
        if (bookingNode == null) {
            return false;
        }
        String bookingId = bookingNode.get("id").textValue();

        testObject.put("bookingId", bookingId);
        boolean isSuccess = user.handleBooking(testObject,bookingEvents);
        // update status
        if (isSuccess) {
            user.updateData(bookingId,bookingEvents);
        }
        return isSuccess;
    }
}
