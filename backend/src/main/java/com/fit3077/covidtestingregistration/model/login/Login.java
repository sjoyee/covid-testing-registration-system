package com.fit3077.covidtestingregistration.model.login;

import com.fit3077.covidtestingregistration.api.UserApi;
import com.fit3077.covidtestingregistration.model.user.User;
import com.fit3077.covidtestingregistration.model.user.UserGenerator;

public class Login {

    private String userName;
    private String password;

    public Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User loginUser() {
        UserApi userApi = new UserApi();

        // user is authenticated successfully by returning the JWT string
        String jwtString = userApi.authenticateUser(this.userName, this.password);

        // generate user if JWT is verified successfully
        if (userApi.verifyToken(jwtString)) {
            return new UserGenerator().generateUser(this.userName, this.password);
        }
        return null;
    }
}
