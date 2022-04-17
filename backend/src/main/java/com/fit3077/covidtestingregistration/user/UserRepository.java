package com.fit3077.covidtestingregistration.user;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserRepository {

    private static final String API_KEY = System.getenv("FIT3077_API_KEY");

    private static final String KEY_NAME = "Authorization";

    private String rootUrl = "https://fit3077.com/api/v1/user";

    public String authenticateUser(String jsonString) {

        String userLoginUrl = rootUrl + "/login";

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

    public ObjectNode[] getUsers() throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(URI.create(rootUrl))
                .setHeader(KEY_NAME, API_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Please specify your API key");
            }

            return new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return new ObjectNode[0];

    }

}
