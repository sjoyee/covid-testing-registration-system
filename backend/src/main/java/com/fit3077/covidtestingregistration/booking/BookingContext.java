package com.fit3077.covidtestingregistration.booking;

public class BookingContext {
    private BookingStrategy strategy;

    public void setStrategy(BookingStrategy strategy) {
        this.strategy = strategy;
    }

    public BookingStrategy getStrategy() {
        return this.strategy;
    }
}
