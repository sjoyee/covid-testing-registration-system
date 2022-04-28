package com.fit3077.covidtestingregistration;

import com.fit3077.covidtestingregistration.login.Login;
import com.fit3077.covidtestingregistration.user.User;

public class Operator {

    private User user;

    public User getUser() {
        return this.user;
    }

    public void createLogin(String userName, String password) {
        Login login = new Login(userName, password);
        this.user = login.loginUser();
    }

    public void createBooking() {
    }

}
