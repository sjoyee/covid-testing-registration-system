package com.fit3077.covidtestingregistration.model;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.model.booking.BookingFacade;
import com.fit3077.covidtestingregistration.model.covidtest.CovidTestFacade;
import com.fit3077.covidtestingregistration.model.testingsite.TestingSite;
import com.fit3077.covidtestingregistration.model.testingsite.TestingSiteFacade;
import com.fit3077.covidtestingregistration.model.user.User;
import com.fit3077.covidtestingregistration.model.user.UserFacade;

public class MainFacade {

    private UserFacade userFacade;
    private BookingFacade bookingFacade;
    private CovidTestFacade covidTestFacade;
    private TestingSiteFacade testingSiteFacade;

    public MainFacade() {
        this.userFacade = new UserFacade();
        this.bookingFacade = new BookingFacade();
        this.covidTestFacade = new CovidTestFacade();
        this.testingSiteFacade = new TestingSiteFacade();
    }

    public User handleLogin(String userName, String password) {
        return this.userFacade.login(userName, password);
    }

    public String getUserRole(String userId) {
        return this.userFacade.getRole(userId);
    }

    public boolean addBooking(String userId, ObjectNode userObject) {
        return this.bookingFacade.createBooking(userId, userObject);
    }

    public String getBookingStatus(String userId, String smsPin) {
        return this.bookingFacade.checkStatus(userId, smsPin);
    }

    public boolean updateTestKitIssued(String userId, String qrCode) {
        return this.bookingFacade.updateHomeTestKit(userId, qrCode);
    }

    public boolean addCovidTest(String userId, ObjectNode testObject) {
        return this.covidTestFacade.createCovidTest(userId, testObject);
    }

    public List<TestingSite> getTestingSite(String inputType, String inputSuburb) {
        return this.testingSiteFacade.filterSite(inputType, inputSuburb);
    }

}
