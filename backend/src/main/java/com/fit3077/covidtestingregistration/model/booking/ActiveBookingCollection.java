package com.fit3077.covidtestingregistration.model.booking;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.api.UserApi;

public class ActiveBookingCollection extends BookingCollection {
    private List<ActiveBooking> activeBookings;

    public ActiveBookingCollection() {
        this.activeBookings = new ArrayList<>();
    }

    public List<ActiveBooking> getActiveBookings() {
        return this.activeBookings;
    }

    public void setActiveBookingsByUserId(String userId) {
        JsonNode arrNode = new UserApi().getBookingsByUserId(userId);
        if (arrNode.isArray()) {
            for (JsonNode bookingNode : arrNode) {
                Booking booking = createBooking((ObjectNode) bookingNode, null);
                if (booking != null && booking.getIsActive()) {
                    this.activeBookings
                            .add(new ActiveBooking(booking.getId(), booking.getTestingSite(), booking.getStartTime(),
                                    booking.getStatus(), booking.getHistories(), booking.getIsActive()));
                }
            }
        }
    }

    public void setActiveBookingByBookingId(String bookingId) {
        ObjectNode bookingNode = new BookingApi().getBookingById(bookingId);
        Booking booking = createBooking(bookingNode, null);
        if (booking != null && booking.getIsActive()) {
            this.activeBookings
                    .add(new ActiveBooking(booking.getId(), booking.getTestingSite(), booking.getStartTime(),
                            booking.getStatus(), booking.getHistories(), booking.getIsActive()));
        }
    }

    public void setActiveBookingByPin(String pin) {
        BookingApi bookingApi = new BookingApi();
        Booking booking;
        for (ObjectNode bookingNode : bookingApi.getBookings()) {
            if (bookingNode.get("smsPin").textValue().equals(pin)) {
                booking = createBooking(bookingNode, null);
                if (booking != null && booking.getIsActive()) {
                    this.activeBookings
                            .add(new ActiveBooking(booking.getId(), booking.getTestingSite(), booking.getStartTime(),
                                    booking.getStatus(), booking.getHistories(), booking.getIsActive()));
                }
            }
        }

    }

}
