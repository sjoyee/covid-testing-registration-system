package com.fit3077.covidtestingregistration.model.booking;

import com.fit3077.covidtestingregistration.model.booking.notification.BookingEventManager;

public class BookingExecutor extends Booking {
    private String customerId;

    private boolean isHomeBooking;

    private boolean hasRatKit;

    private String patientId;

    private BookingContext context;

    private BookingEventManager bookingEvents;

    public BookingExecutor(String customerId, String startTime, boolean isHomeBooking,
            BookingEventManager bookingEvents) {
        super();
        this.customerId = customerId;
        this.isHomeBooking = isHomeBooking;

        this.bookingEvents = bookingEvents;

        setStartTime(startTime);

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
            this.context.setStrategy(new OnsiteBookingStrategy(getTestingSite().getId(), this.bookingEvents));
        }
    }

    public String assignBookingDetails() {
        this.setBookingStrategy();
        return this.context.getStrategy().executeBooking(this.customerId, getStartTime());
    }

}
