package com.fit3077.covidtestingregistration.model.user;

import com.fit3077.covidtestingregistration.model.login.Login;

public class UserFacade {
    public User login(String userName, String password) {
        return new Login(userName, password).loginUser();
    }

    public String getRole(String userId) {
        return new UserGenerator().generateUser(userId).toString();
    }
}
