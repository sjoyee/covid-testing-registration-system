package com.fit3077.covidtestingregistration.model.user;

import com.fit3077.covidtestingregistration.model.login.Login;
import com.fit3077.covidtestingregistration.model.observer.BookingEventManager;

public class UserFacade {
    public User login(String userName, String password,BookingEventManager bookingEvents) {
        return new Login(userName, password).loginUser(bookingEvents);
    }

    public String getRole(String userId, BookingEventManager bookingEvents) {
        return new UserGenerator().generateUser(userId,bookingEvents).toString();
    }
}
