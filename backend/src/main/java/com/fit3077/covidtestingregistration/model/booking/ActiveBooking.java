package com.fit3077.covidtestingregistration.model.booking;

import java.time.Instant;
import java.util.List;
import java.util.ListIterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.model.booking.memento.BookingHistory;
import com.fit3077.covidtestingregistration.model.booking.memento.BookingMemento;
import com.fit3077.covidtestingregistration.model.testingsite.TestingSite;

// A type of Booking class which is active and able to be modified and restored
public class ActiveBooking extends Booking {

    public ActiveBooking(String id, TestingSite testingSite, String startTime, BookingStatus status,
            List<BookingHistory> histories, boolean isActive) {
        super(id, testingSite, startTime, status, histories, isActive);
        // always check validity of all datetimes of the booking histories
        checkValidity();
    }

    public BookingMemento createMemento() {
        return new BookingMemento(getHistories(), getId(), getTestingSite().getId(), getStartTime());
    }

    public void updateChanges(String testingSiteId, String dateTime) {
        setTestingSite(new TestingSite(testingSiteId));
        setStartTime(dateTime);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();
        updatedNode.put("testingSiteId", getTestingSite().getId());
        updatedNode.put("startTime", getStartTime());

        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(getId(), updatedNode);
    }

    public void updateChanges(BookingStatus status) {
        setStatus(status);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();
        updatedNode.put("status", getStatus().name());

        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(getId(), updatedNode);
    }

    private void checkValidity() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();

        String additionalInfo = "additionalInfo";
        updatedNode.with(additionalInfo).put("isHomeBooking", false);
        ArrayNode arrayNode = updatedNode.with(additionalInfo).putArray("snapshots");

        // iterate snapshots to remove the lapsed changes
        ListIterator<BookingHistory> itr = getHistories().listIterator();
        while (itr.hasNext()) {
            BookingHistory history = itr.next();
            // remove booking which is lapsed / expired
            if (Instant.parse(history.getDateTime()).isBefore(Instant.now())) {
                itr.remove();
            } else {
                arrayNode.add(mapper.valueToTree(history));
            }
        }
        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(getId(), updatedNode);
    }

    // for deleting active booking
    public void deleteBooking() {
        BookingApi bookingApi = new BookingApi();
        bookingApi.deleteActiveBooking(this.getId());
    }
}
