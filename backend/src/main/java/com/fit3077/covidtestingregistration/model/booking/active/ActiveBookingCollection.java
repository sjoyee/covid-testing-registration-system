package com.fit3077.covidtestingregistration.model.booking.active;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.api.UserApi;
import com.fit3077.covidtestingregistration.model.booking.BookingStatus;

public class ActiveBookingCollection {

    private List<ActiveBooking> activeBookings;

    public ActiveBookingCollection() {
        this.activeBookings = new ArrayList<>();
    }

    private ActiveBooking createActiveBooking(ObjectNode bookingNode) {
        String status = bookingNode.get("status").textValue();
        Instant dateTime = Instant.parse(bookingNode.get("startTime").textValue());
        boolean isHomeBooking = bookingNode.get("additionalInfo").get("isHomeBooking").asBoolean();
        // only onsite bookings which are not lapsed, not completed and valid are
        // considered
        if (dateTime.isAfter(Instant.now()) && !status.equals(BookingStatus.COMPLETED.name())
                && !status.equals(BookingStatus.INVALID.name()) && !isHomeBooking) {

            String id = bookingNode.get("id").textValue();

            String testingSiteId = bookingNode.get("testingSite").get("id").textValue();

            List<ActiveBookingHistory> histories = new ObjectMapper().convertValue(
                    bookingNode.get("additionalInfo").get("snapshots"),
                    new TypeReference<List<ActiveBookingHistory>>() {
                    });

            return new ActiveBooking(id, testingSiteId, status, dateTime.toString(), histories);
        }
        return null;
    }

    public void setActiveBookingsByUserId(String userId) {
        JsonNode arrNode = new UserApi().getBookingsByUserId(userId);
        if (arrNode.isArray()) {
            for (JsonNode bookingNode : arrNode) {
                ActiveBooking activeBooking = createActiveBooking((ObjectNode) bookingNode);
                if (activeBooking != null) {
                    this.activeBookings.add(activeBooking);
                }
            }
        }
    }

    public void setActiveBookingByBookingId(String bookingId) {
        ObjectNode bookingNode = new BookingApi().getBookingById(bookingId);

        ActiveBooking activeBooking = createActiveBooking(bookingNode);
        if (activeBooking != null) {
            this.activeBookings.add(activeBooking);
        }
    }

    public List<ActiveBooking> getActiveBookings() {
        return this.activeBookings;
    }
}
