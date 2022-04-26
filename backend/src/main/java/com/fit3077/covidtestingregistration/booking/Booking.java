package com.fit3077.covidtestingregistration.booking;

import java.time.Instant;

import com.fit3077.covidtestingregistration.api.BookingApi;

public class Booking {
    private String customerId;

    private String testingSiteId;

    private Instant startTime;

    private boolean success;

    private BookingStatus status;

    private BookingApi bookingApi;

    public Booking(String customerId, String testingSiteId) {
        this.customerId = customerId;
        this.testingSiteId = testingSiteId;
        this.startTime = Instant.now();

        this.status = BookingStatus.INITIATED;

        bookingApi = new BookingApi();

        this.success = this.bookingApi.createNewBooking(this.customerId, this.testingSiteId, this.startTime.toString());
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public String getTestingSiteId() {
        return this.testingSiteId;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public BookingStatus getStatus() {
        return this.status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

}
