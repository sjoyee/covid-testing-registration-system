package com.fit3077.covidtestingregistration.model.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.api.UserApi;
import com.fit3077.covidtestingregistration.model.booking.BookingExecutor;
import com.fit3077.covidtestingregistration.model.testingsite.TestingSite;

public class Receptionist extends BookingUser {

    private TestingSite testingSite;

    protected Receptionist(String id, String givenName, String familyName, String userName, String phoneNumber,
            String testingSiteId) {
        super(id, givenName, familyName, userName, phoneNumber);
        testingSite = new TestingSite(testingSiteId);
        setIsReceptionist(true);
    }

    public void setTestingSite(TestingSite testingSite) {
        this.testingSite = testingSite;
    }

    public TestingSite getTestingSite() {
        return testingSite;
    }

    @Override
    public String toString() {
        return "RECEPTIONIST";
    }

    @Override
    public boolean handleBooking(ObjectNode userObject) {
        String customerId = "";
        String userName = userObject.get("userName").textValue();

        UserApi userApi = new UserApi();

        for (ObjectNode userNode : userApi.getUsers()) {
            if (userNode.get("userName").textValue().equals(userName)) {
                customerId = userNode.get("id").textValue();
                break;
            }
        }
        if (customerId.isEmpty()) {
            userObject.put("isCustomer", true);
            userObject.put("isReceptionist", false);
            userObject.put("isHealthcareWorker", false);

            customerId = userApi.createNewUser(userObject).get("id").textValue();
        }
        boolean isHomeBooking = userObject.get("isHomeBooking").asBoolean();

        BookingExecutor booking = new BookingExecutor(customerId, isHomeBooking);

        if (!isHomeBooking) {
            booking.setTestingSite(testingSite);
        }

        return booking.assignBookingDetails();
    }

    @Override
    public boolean updateData(String qrCode) {
        BookingApi bookingApi = new BookingApi();
        String additionalInfo = "additionalInfo";

        for (ObjectNode bookingNode : bookingApi.getBookings()) {
            if (!bookingNode.get(additionalInfo).toString().equals("{}")
                    && bookingNode.get(additionalInfo).get("isHomeBooking").asBoolean()
                    && bookingNode.get(additionalInfo).get("qrCode").textValue().equals(qrCode)) {

                if (!bookingNode.get(additionalInfo).get("hasRatKit").asBoolean()) {

                    bookingNode.with(additionalInfo).put("hasRatKit", true);
                    return bookingApi.updateTestKitData(bookingNode.get("id").textValue(), bookingNode);
                }
            }
        }
        return false;
    }

    // when admin change smtg, it will notidy the event manager (so there will be an
    // event manager object)

    // @Override
    // public ActiveBooking modifyActiveBooking(ActiveBooking activeBooking, String
    // newTestingSiteId, String newDateTime) {
    // bookingEvents.notify("modify",activeBooking);
    // return super.modifyActiveBooking(activeBooking,newTestingSiteId,newDateTime);

    // }

    // public ActiveBooking restorePastChange(ActiveBooking activeBooking, String
    // testingSiteId, String dateTime) {
    // bookingEvents.notify("modify",activeBooking);
    // return super.restorePastChange(activeBooking,testingSiteId,dateTime);
    // }

    // public void cancelActiveBooking(ActiveBooking activeBooking) {
    // super.cancelActiveBooking(activeBooking);
    // bookingEvents.notify("cancel",activeBooking);
    // }

}
