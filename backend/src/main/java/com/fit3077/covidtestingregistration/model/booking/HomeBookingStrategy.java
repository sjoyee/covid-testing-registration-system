package com.fit3077.covidtestingregistration.model.booking;

import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.BookingApi;
import com.fit3077.covidtestingregistration.model.booking.notification.BookingEventManager;
import com.fit3077.covidtestingregistration.model.covidtest.CovidTest;
import com.fit3077.covidtestingregistration.model.covidtest.CovidTestType;

public class HomeBookingStrategy implements BookingStrategy {
    private Random rand = new Random();

    private boolean hasRatKit;
    private String patientId;
    private String status;

    public HomeBookingStrategy(boolean hasRatKit, String patientId, String status) {
        this.hasRatKit = hasRatKit;
        this.patientId = patientId;
        this.status = status;
    }

    @Override
    public String executeBooking(String customerId, String startTime) {
        BookingApi bookingApi = new BookingApi();
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode bookingNode = mapper.createObjectNode();
        bookingNode.put("customerId", customerId);
        bookingNode.put("startTime", startTime);

        String additionalInfo = "additionalInfo";

        bookingNode.with(additionalInfo).put("isHomeBooking", true);
        bookingNode.with(additionalInfo).put("qrCode", generateRandStr(8));
        bookingNode.with(additionalInfo).put("url", generateUrl());
        bookingNode.with(additionalInfo).put("hasRatKit", this.hasRatKit);

        ObjectNode resultNode = bookingApi.createNewBooking(bookingNode);
        if (resultNode == null) {
            return null;
        }
        String bookingId = resultNode.get("id").textValue();

        CovidTest covidTest = new CovidTest(this.patientId, CovidTestType.RAT, bookingId);

        String id = covidTest.assignCovidTestDetails();
        if (id != null) {
            bookingApi.updateBookingStatus(bookingId, this.status);
            return id;
        }
        return null;
    }

    private String generateRandStr(int length) {

        String possibleChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder qrCode = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randIndex = rand.nextInt(possibleChar.length());
            qrCode.append(possibleChar.charAt(randIndex));
        }
        return qrCode.toString();
    }

    private String generateUrl() {
        String url = "https://covid19-home-testing/";
        url += generateRandStr(10);
        return url;
    }

}
