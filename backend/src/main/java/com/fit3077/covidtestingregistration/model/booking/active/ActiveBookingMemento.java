package com.fit3077.covidtestingregistration.model.booking.active;

import java.time.Instant;
import java.util.List;
import java.util.ListIterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;

public class ActiveBookingMemento {

    private List<ActiveBookingHistory> historyList;
    private String bookingId;
    private String testingSiteId;
    private String dateTime;

    public ActiveBookingMemento(List<ActiveBookingHistory> historyList, String bookingId, String testingSiteId,
            String dateTime) {
        this.historyList = historyList;
        this.bookingId = bookingId;
        this.testingSiteId = testingSiteId;
        this.dateTime = dateTime;
    }

    public void restore(String restoreTestingSiteId, String restoreDateTime) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();
        String additionalInfo = "additionalInfo";

        updatedNode.with(additionalInfo).put("isHomeBooking", false);
        ArrayNode arrayNode = updatedNode.with(additionalInfo).putArray("snapshots");

        // add the current testing site id and datetime to the history list since it
        // will be considered as past changes
        addLatestRecord(this.testingSiteId, this.dateTime);

        ListIterator<ActiveBookingHistory> itr = this.historyList.listIterator();
        while (itr.hasNext()) {
            ActiveBookingHistory history = itr.next();
            if (history.getTestingSiteId().equals(restoreTestingSiteId)
                    && history.getDateTime().equals(restoreDateTime)) {
                // remove the to-restore version
                itr.remove();

            } else {
                arrayNode.add(mapper.valueToTree(history));
            }
        }

        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(this.bookingId, updatedNode);

    }

    public void update() {
        // the first item is always the oldest / least recent past record
        this.historyList.remove(0);

        addLatestRecord(this.testingSiteId, this.dateTime);

        // update api
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode updatedNode = mapper.createObjectNode();

        String additionalInfo = "additionalInfo";
        updatedNode.with(additionalInfo).put("isHomeBooking", false);
        ArrayNode arrayNode = updatedNode.with(additionalInfo).putArray("snapshots");

        ListIterator<ActiveBookingHistory> itr = this.historyList.listIterator();
        while (itr.hasNext()) {
            ActiveBookingHistory history = itr.next();
            arrayNode.add(mapper.valueToTree(history));
        }

        BookingApi bookingApi = new BookingApi();
        bookingApi.updateActiveBooking(this.bookingId, updatedNode);

    }

    private void addLatestRecord(String testingSiteId, String dateTime) {
        String updatedAt = Instant.now().toString();
        ActiveBookingHistory history = new ActiveBookingHistory(updatedAt, testingSiteId, dateTime);
        this.historyList.add(history);
    }

}
