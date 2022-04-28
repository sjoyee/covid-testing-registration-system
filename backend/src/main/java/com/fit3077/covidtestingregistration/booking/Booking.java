package com.fit3077.covidtestingregistration.booking;

import java.time.Instant;

public class Booking {
    private String customerId;

    private Instant startTime;

    private String testingSiteId;

    private boolean isHomeBooking;

    private boolean hasRatKit;

    private BookingStatus status;

    private BookingContext context;

    public Booking(String customerId, boolean isHomeBooking) {
        this.customerId = customerId;
        this.isHomeBooking = isHomeBooking;
        this.startTime = Instant.now();
        this.status = BookingStatus.INITIATED;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public boolean getIsHomeBooking() {
        return this.isHomeBooking;
    }

    public boolean getHasRatKit() {
        return this.hasRatKit;
    }

    public String getTestingSiteId() {
        return this.testingSiteId;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public BookingStatus getStatus() {
        return this.status;
    }

    public void setTestingSiteId(String testingSiteId) {
        this.testingSiteId = testingSiteId;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setHasRatKit(boolean hasRatKit) {
        this.hasRatKit = hasRatKit;
    }

    public void setBookingStrategy() {
        this.context = new BookingContext();
        if (this.isHomeBooking) {
            this.context.setStrategy(new HomeBookingStrategy(this.hasRatKit));
        } else {
            this.context.setStrategy(new OnsiteBookingStrategy(this.testingSiteId));
        }
    }

    public boolean assignBookingDetails() {
        this.setBookingStrategy();
        return this.context.getStrategy().executeBooking(this.customerId, this.startTime.toString());
    }

}
