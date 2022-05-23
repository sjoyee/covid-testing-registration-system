package com.fit3077.covidtestingregistration.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BookingApi {

    private static final String API_KEY = System.getenv("FIT3077_API_KEY");

    private static final String KEY_NAME = "Authorization";

    private String rootUrl = "https://fit3077.com/api/v1/booking";

    public ObjectNode createNewBooking(ObjectNode bookingNode) {

        String jsonString = bookingNode.toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(rootUrl))
                .setHeader(KEY_NAME, API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new ObjectMapper().readValue(response.body(), ObjectNode.class);

        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;

    }

    public ObjectNode[] getBookings() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(rootUrl))
                .setHeader(KEY_NAME, API_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        } catch (InterruptedException e0) {
            e0.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e1) {
            e1.printStackTrace();

        }

        return new ObjectNode[0];

    }

    public ObjectNode getBookingById(String bookingId) {

        String bookingUrl = rootUrl + '/' + bookingId;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingUrl))
                .setHeader(KEY_NAME, API_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new ObjectMapper().readValue(response.body(), ObjectNode.class);

        } catch (InterruptedException e0) {
            e0.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e1) {
            e1.printStackTrace();

        }

        return null;

    }

    public boolean updateTestKitData(String bookingId, ObjectNode additionalNode) {

        if (bookingId.isEmpty()) {
            return false;
        }

        String bookingUrl = rootUrl + '/' + bookingId;
        String jsonString = additionalNode.toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader(KEY_NAME, API_KEY)
                .uri(URI.create(bookingUrl))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
                .header("Content-Type", "application/json")
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            // return true if update is successful
            return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e) {
            e.printStackTrace();

        }
        // if update is unsuccessful, return false
        return false;

    }

    public void updateBookingStatus(String bookingId, String status) {

        String bookingUrl = rootUrl + '/' + bookingId;
        String jsonString = "{" +
                "\"status\":\"" + status + "\"" +
                "}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader(KEY_NAME, API_KEY)
                .uri(URI.create(bookingUrl))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
                .header("Content-Type", "application/json")
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void updateActiveBooking(String bookingId, ObjectNode updatedNode) {

        String bookingUrl = rootUrl + '/' + bookingId;
        String jsonString = updatedNode.toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader(KEY_NAME, API_KEY)
                .uri(URI.create(bookingUrl))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonString))
                .header("Content-Type", "application/json")
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}