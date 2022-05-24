package com.fit3077.covidtestingregistration.model.booking;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class BookingExecutor extends Booking {
    private String customerId;

    private boolean isHomeBooking;

    private boolean hasRatKit;

    private String patientId;

    private BookingContext context;

    public BookingExecutor(String customerId, boolean isHomeBooking) {
        super();
        this.customerId = customerId;
        this.isHomeBooking = isHomeBooking;

        LocalDateTime datetime = LocalDateTime.of(2023, 12, 21, 13, 15);
        setStartTime(datetime.toInstant(ZoneOffset.UTC).toString());

        if (isHomeBooking) {
            // already assigned with RAT test type
            setStatus(BookingStatus.PROCESSED);
        } else {
            // have not assigned test type
            setStatus(BookingStatus.INITIATED);
        }
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

    public void setHasRatKit(boolean hasRatKit) {
        this.hasRatKit = hasRatKit;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setBookingStrategy() {
        this.context = new BookingContext();
        if (this.isHomeBooking) {
            this.context.setStrategy(new HomeBookingStrategy(this.hasRatKit, this.patientId, getStatus().toString()));
        } else {
            this.context.setStrategy(new OnsiteBookingStrategy(getTestingSiteId()));
        }
    }

    public boolean assignBookingDetails() {
        this.setBookingStrategy();
        return this.context.getStrategy().executeBooking(this.customerId, getStartTime());
    }

}
