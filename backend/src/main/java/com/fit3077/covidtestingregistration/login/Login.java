package com.fit3077.covidtestingregistration.login;

import com.fit3077.covidtestingregistration.api.UserApi;
import com.fit3077.covidtestingregistration.user.User;
import com.fit3077.covidtestingregistration.user.UserGenerator;

public class Login {

    private String userName;
    private String password;

    public Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User loginUser() {
        UserApi userApi = new UserApi();

        String jwtString = userApi.authenticateUser(this.userName, this.password);

        if (userApi.verifyToken(jwtString)) {
            UserGenerator userGenerator = new UserGenerator();
            userGenerator.createUser(this.userName, this.password);
            return userGenerator.getUser();

        } else {
            return null;
        }
    }
}
