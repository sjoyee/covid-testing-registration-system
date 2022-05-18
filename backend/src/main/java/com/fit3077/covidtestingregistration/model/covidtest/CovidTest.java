package com.fit3077.covidtestingregistration.model.covidtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.api.CovidTestApi;

public class CovidTest {
    private String patientId;
    private CovidTestType testType;
    private String bookingId;

    public CovidTest(String patientId, CovidTestType testType, String bookingId) {
        this.patientId = patientId;
        this.testType = testType;
        this.bookingId = bookingId;
    }

    public boolean assignCovidTestDetails() {
        CovidTestApi covidTestApi = new CovidTestApi();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode testNode = mapper.createObjectNode();
        testNode.put("patientId", this.patientId);
        testNode.put("type", this.testType.toString());
        testNode.put("bookingId", this.bookingId);

        return covidTestApi.createNewCovidTest(testNode);
    }
}
