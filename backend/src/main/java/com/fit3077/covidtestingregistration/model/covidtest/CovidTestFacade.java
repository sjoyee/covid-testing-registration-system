package com.fit3077.covidtestingregistration.model.covidtest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.user.User;
import com.fit3077.covidtestingregistration.model.user.UserGenerator;

public class CovidTestFacade {
    public boolean createCovidTest(String userId, ObjectNode testObject) {
        // get booking object
        User user = new UserGenerator().generateUser(userId);
        ObjectNode bookingNode = user.checkPinCode(testObject.get("smsPin").textValue());
        if (bookingNode == null) {
            return false;
        }
        String bookingId = bookingNode.get("id").textValue();

        testObject.put("bookingId", bookingId);
        boolean isSuccess = user.handleBooking(testObject,null);
        // update status
        if (isSuccess) {
            user.updateData(bookingId);
        }
        return isSuccess;
    }
}
