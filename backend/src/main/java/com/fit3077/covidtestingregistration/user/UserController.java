package com.fit3077.covidtestingregistration.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    private UserFactory userFactory;

    public UserController() {
        userFactory = new UserFactory();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginUser loginUser) {
        this.userFactory.createUser(loginUser);
        if (this.userFactory.getUser() == null) {
            return new ResponseEntity<>(this.userFactory.getUser(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(this.userFactory.getUser(), HttpStatus.OK);
    }
}
