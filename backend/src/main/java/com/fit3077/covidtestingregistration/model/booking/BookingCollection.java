package com.fit3077.covidtestingregistration.model.booking;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.TestingSiteApi;
import com.fit3077.covidtestingregistration.model.booking.memento.BookingHistory;

public class BookingCollection {

    private List<Booking> bookings;
    private

    public BookingCollection() {
        this.bookings = new ArrayList<>();
    }

    public List<Booking> getBookings() {
        return this.bookings;
    }

    private boolean checkIsActive(String startTime, BookingStatus status) {
        return Instant.parse(startTime).isAfter(Instant.now()) && !status.equals(BookingStatus.COMPLETED)
                && !status.equals(BookingStatus.CANCELLED) && !status.equals(BookingStatus.INVALID);
    }

    protected Booking createBooking(ObjectNode bookingNode) {
        boolean isHomeBooking = bookingNode.get("additionalInfo").get("isHomeBooking").asBoolean();

        if (!isHomeBooking) {
            String status = bookingNode.get("status").textValue();
            String startTime = Instant.parse(bookingNode.get("startTime").textValue()).toString();

            boolean isActive = checkIsActive(startTime, BookingStatus.valueOf(status));

            String id = bookingNode.get("id").textValue();

            String testingSiteId = bookingNode.get("testingSite").get("id").textValue();

            List<BookingHistory> histories = new ObjectMapper().convertValue(
                    bookingNode.get("additionalInfo").get("snapshots"),
                    new TypeReference<List<BookingHistory>>() {
                    });

            return new Booking(id, testingSiteId, startTime, BookingStatus.valueOf(status),
                    histories, isActive);
        }
        return null;
    }

    public void setBookingsByTestingSiteId(String testingSiteId) {
        JsonNode arrNode = new TestingSiteApi().getBookingsByTestingSiteId(testingSiteId);
        if (arrNode.isArray()) {
            for (JsonNode bookingNode : arrNode) {
                Booking booking = createBooking((ObjectNode) bookingNode);
                if (booking != null) {
                    this.bookings.add(booking);
                }
            }
        }
    }

    
}
