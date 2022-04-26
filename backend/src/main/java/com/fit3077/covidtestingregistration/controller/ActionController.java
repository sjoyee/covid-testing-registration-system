package com.fit3077.covidtestingregistration.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.user.LoginUser;
import com.fit3077.covidtestingregistration.user.User;
import com.fit3077.covidtestingregistration.user.UserFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class ActionController {

    private UserFactory userFactory;

    public ActionController() {
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

    @PostMapping("/onsite-booking")
    public ResponseEntity<Void> onsiteBooking(@RequestBody ObjectNode userObject) {
        boolean successful = this.userFactory.getUser().handleBooking(userObject);
        if (successful) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check-status")
    public ResponseEntity<String> checkStatus(@RequestParam("pin") String pin) {
        String status = this.userFactory.getUser().checkStatus(pin);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
