package com.fit3077.covidtestingregistration.model.booking.active;

import java.time.Instant;
import java.util.List;
import java.util.ListIterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;

public class ActiveBookingMemento {
    private String bookingId;
    private List<ActiveBookingHistory> historyList;

    public ActiveBookingMemento(String bookingId, List<ActiveBookingHistory> historyList) {
        this.bookingId = bookingId;
        this.historyList = historyList;
        // always check validity of all datetimes of the booking histories
        checkValidity();
    }

    public void restore(String updatedAt, String currTestingSiteId, String currDateTime) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();
        String additionalInfo = "additionalInfo";

        updatedNode.with(additionalInfo).put("isHomeBooking", false);
        ArrayNode arrayNode = updatedNode.with(additionalInfo).putArray("snapshots");

        // add the current testing site id and datetime to the history list since it
        // will be considered as past changes
        addLatestRecord(currTestingSiteId, currDateTime);

        ListIterator<ActiveBookingHistory> itr = this.historyList.listIterator();
        while (itr.hasNext()) {
            if (itr.next().getUpdatedAt().equals(updatedAt)) {
                // update restored version
                String restoreTestingId = itr.next().getTestingSiteId();
                String restoreDateTime = itr.next().getDateTime();

                updatedNode.put("testingSiteId", restoreTestingId);
                updatedNode.put("startTime", restoreDateTime);

                // remove the to-restore version
                itr.remove();

            } else {
                arrayNode.add(mapper.valueToTree(itr.next()));
            }
        }

        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(this.bookingId, updatedNode);

    }

    public void update(String testingSiteId, String dateTime) {
        removeLeastRecent();
        addLatestRecord(testingSiteId, dateTime);

        // update api
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();

        String additionalInfo = "additionalInfo";
        updatedNode.with(additionalInfo).put("isHomeBooking", false);
        ArrayNode arrayNode = updatedNode.with(additionalInfo).putArray("snapshots");

        ListIterator<ActiveBookingHistory> itr = this.historyList.listIterator();
        while (itr.hasNext()) {
            arrayNode.add(mapper.valueToTree(itr.next()));
        }

        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(this.bookingId, updatedNode);

    }

    private void checkValidity() {
        ListIterator<ActiveBookingHistory> itr = this.historyList.listIterator();
        while (itr.hasNext()) {
            // remove booking which is lapsed / expired
            if (Instant.parse(itr.next().getDateTime()).isBefore(Instant.now())) {
                itr.remove();
            }
        }
    }

    private void removeLeastRecent() {
        // the first item is always the oldest / least recent past record
        this.historyList.remove(0);
    }

    private void addLatestRecord(String testingSiteId, String dateTime) {
        String updatedAt = Instant.now().toString();
        ActiveBookingHistory history = new ActiveBookingHistory(updatedAt, testingSiteId, dateTime);
        this.historyList.add(history);
    }

}
