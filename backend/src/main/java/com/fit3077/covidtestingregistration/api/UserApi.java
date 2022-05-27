package com.fit3077.covidtestingregistration.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserApi {

    private static final String API_KEY = System.getenv("FIT3077_API_KEY");

    private static final String KEY_NAME = "Authorization";

    private String rootUrl = "https://fit3077.com/api/v2/user";

    public String authenticateUser(String userName, String password) {

        String userLoginUrl = rootUrl + "/login";

        String jsonString = "{" +
                "\"userName\":\"" + userName + "\"," +
                "\"password\":\"" + password + "\"" +
                "}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(userLoginUrl + "?jwt=true"))
                .setHeader(KEY_NAME, API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectNode jsonNode = new ObjectMapper().readValue(response.body(), ObjectNode.class);
            return "{\"jwt\":\"" + jsonNode.get("jwt").textValue() + "\"}";

        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verifyToken(String jwtString) {

        String userVerifyUrl = rootUrl + "/verify-token";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(userVerifyUrl))
                .setHeader(KEY_NAME, API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jwtString))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;

        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;
    }

    public ObjectNode getUserById(String id) {

        String userIdUrl = rootUrl + '/' + id;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(userIdUrl))
                .setHeader(KEY_NAME, API_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new InvalidKeyException("Please specify your API key");
            }

            return new ObjectMapper().readValue(response.body(), ObjectNode.class);

        } catch (InterruptedException e0) {
            e0.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e1) {
            e1.printStackTrace();

        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
        }

        return null;

    }

    public ObjectNode[] getUsers() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(rootUrl))
                .setHeader(KEY_NAME, API_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new InvalidKeyException("Please specify your API key");
            }

            return new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        } catch (InterruptedException e0) {
            e0.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e1) {
            e1.printStackTrace();

        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
        }

        return new ObjectNode[0];

    }

    // return user id
    public ObjectNode createNewUser(ObjectNode userObject) {
        String jsonString = "{" +
                "\"givenName\":\"" + userObject.get("givenName").textValue() + "\"," +
                "\"familyName\":\"" + userObject.get("familyName").textValue() + "\"," +
                "\"userName\":\"" + userObject.get("userName").textValue() + "\"," +
                "\"password\":\"" + userObject.get("password").textValue() + "\"," +
                "\"phoneNumber\":\"" + userObject.get("phoneNumber").textValue() + "\"," +
                "\"isCustomer\":" + userObject.get("isCustomer").asBoolean() + "," +
                "\"isReceptionist\":" + userObject.get("isReceptionist").asBoolean() + "," +
                "\"isHealthcareWorker\":" + userObject.get("isHealthcareWorker").asBoolean() +
                "}";

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

    public JsonNode getBookingsByUserId(String userId) {

        String bookingUrl = rootUrl + '/' + userId + "?fields=bookings";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingUrl))
                .setHeader(KEY_NAME, API_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new InvalidKeyException("Please specify your API key");
            }

            return new ObjectMapper().readTree(response.body()).get("bookings");

        } catch (InterruptedException e0) {
            e0.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e1) {
            e1.printStackTrace();

        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
        }

        return null;

    }

}
