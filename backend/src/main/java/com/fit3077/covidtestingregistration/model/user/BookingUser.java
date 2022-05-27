package com.fit3077.covidtestingregistration.model.user;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.UserApi;
import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;
import com.fit3077.covidtestingregistration.model.booking.Booking;
import com.fit3077.covidtestingregistration.model.booking.BookingCollection;
import com.fit3077.covidtestingregistration.model.booking.BookingModifier;
import com.fit3077.covidtestingregistration.model.notification.BookingEventListener;

public abstract class BookingUser extends User {
    private BookingEventListener listener;

    protected BookingUser(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
    }

    public abstract boolean handleBooking(ObjectNode objectNode);

    public abstract boolean updateData(String code);

    public ActiveBooking modifyActiveBooking(ActiveBooking activeBooking, String newTestingSiteId, String newDateTime) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.saveHistories();
        modifier.modify(newTestingSiteId, newDateTime, this.getId());
        return activeBooking;
    }

    public ActiveBooking restorePastChange(ActiveBooking activeBooking, String testingSiteId, String dateTime) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.saveHistories();
        modifier.restore(testingSiteId, dateTime, this.getId());
        return activeBooking;
    }

    public void cancelActiveBooking(ActiveBooking activeBooking) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.cancel(this.getId());
    }

    // check
    public List<Booking> getBookingByTestingSiteId() {
        BookingCollection bookings = new BookingCollection();
        UserApi userApi = new UserApi();
        for (ObjectNode userNode : userApi.getUsers()) {
            if (userNode.get("isReceptionist").asBoolean()) {
                if (userNode.get("id").toString() == this.getId()) {
                    String testingSiteId = userNode.get("additionalInfo").get("testingSiteId").textValue();
                    bookings.setBookingsByTestingSiteId(testingSiteId);

                }

            }

        }
        return bookings.getBookings();
    }

    public void checkValidityToBeNotified(){
        

    }

}
