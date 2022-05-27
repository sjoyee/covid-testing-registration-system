package com.fit3077.covidtestingregistration.model.user;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.UserApi;
import com.fit3077.covidtestingregistration.model.booking.ActiveBooking;
import com.fit3077.covidtestingregistration.model.booking.Booking;
import com.fit3077.covidtestingregistration.model.booking.BookingCollection;
import com.fit3077.covidtestingregistration.model.booking.BookingModifier;
import com.fit3077.covidtestingregistration.model.notification.BookingEventManager;

public abstract class BookingUser extends User {
    private BookingEventManager bookingEvents = new BookingEventManager();

    protected BookingUser(String id, String givenName, String familyName, String userName, String phoneNumber) {
        super(id, givenName, familyName, userName, phoneNumber);
    }

    public abstract boolean handleBooking(ObjectNode objectNode);

    public abstract boolean updateData(String code);

    public ActiveBooking modifyActiveBooking(ActiveBooking activeBooking, String newTestingSiteId, String newDateTime) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.saveHistories();
        modifier.modify(newTestingSiteId, newDateTime, this.getId(), this.bookingEvents);
        return activeBooking;
    }

    public ActiveBooking restorePastChange(ActiveBooking activeBooking, String testingSiteId, String dateTime) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.saveHistories();
        modifier.restore(testingSiteId, dateTime, this.getId(), this.bookingEvents);
        return activeBooking;
    }

    public void cancelActiveBooking(ActiveBooking activeBooking) {
        BookingModifier modifier = new BookingModifier(activeBooking);
        modifier.cancel(this.getId(), this.bookingEvents);
    }

    public void deleteActiveBooking(ActiveBooking activeBooking) {
        if (isReceptionist()) {
            BookingModifier modifier = new BookingModifier(activeBooking);
            modifier.delete(this.getId(), this.bookingEvents);
        } else {
            System.out.println("No permission");
        }

    }

    public String retrieveBookingUpdate() {
        return this.bookingEvents.getNotifyListener().notifyUser(this.getId());

    }

    // check
    public List<Booking> retrieveBookingByTestingSiteId() {
        BookingCollection bookings = new BookingCollection();
        if (isReceptionist()) {

            bookings.setBookingsByTestingSiteId(haveTestingSite());
        }
        return bookings.getBookings();
    }

    public String haveTestingSite() {
        UserApi userApi = new UserApi();
        for (ObjectNode userNode : userApi.getUsers()) {
            if (userNode.get("id").toString() == this.getId()) {
                if (userNode.get("isReceptionist").asBoolean()) {
                    String testingSiteId = userNode.get("additionalInfo").get("testingSiteId").textValue();
                    return testingSiteId;
                }
            }
        }
        return null;
    }

    private boolean isReceptionist() {
        if (haveTestingSite() != null) {
            return true;
        }
        return false;
    }

}
