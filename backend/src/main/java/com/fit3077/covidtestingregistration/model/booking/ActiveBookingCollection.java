package com.fit3077.covidtestingregistration.model.booking;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fit3077.covidtestingregistration.api.UserApi;

public class ActiveBookingCollection {

    private String userId;
    private List<ActiveBooking> activeBookings;

    public ActiveBookingCollection(String userId) {
        this.userId = userId;
        this.activeBookings = new ArrayList<>();
        setActiveBookingCollection();
    }

    public void setActiveBookingCollection() {
        JsonNode arrNode = new UserApi().getBookingsByUserId(this.userId);
        if (arrNode.isArray()) {
            for (JsonNode bookingNode : arrNode) {

                Instant startTime = Instant.parse(bookingNode.get("startTime").textValue());

                String status = bookingNode.get("status").textValue();

                // only bookings which are not lapsed, not completed and valid are considered
                if (startTime.isAfter(Instant.now()) && !status.equals(BookingStatus.COMPLETED.name())
                        && !status.equals(BookingStatus.INVALID.name())) {

                    String id = bookingNode.get("id").textValue();
                    boolean isHomeBooking = bookingNode.get("additionalInfo").get("isHomeBooking").asBoolean();

                    String testingSiteName;

                    if (isHomeBooking) {
                        testingSiteName = "";
                    } else {
                        testingSiteName = bookingNode.get("testingSite").get("name").textValue();
                    }

                    ActiveBooking booking = new ActiveBooking(id, testingSiteName, status, startTime);
                    this.activeBookings.add(booking);
                }
            }
        }
    }

    public List<ActiveBooking> getActiveBookings() {
        return this.activeBookings;
    }
}
