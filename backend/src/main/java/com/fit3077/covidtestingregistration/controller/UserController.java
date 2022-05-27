package com.fit3077.covidtestingregistration.controller;

import com.fit3077.covidtestingregistration.model.MainFacade;
import com.fit3077.covidtestingregistration.model.user.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    private MainFacade mainFacade;

    public UserController() {
        mainFacade = new MainFacade();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam("userName") String userName,
            @RequestParam("password") String password) {
        User user = this.mainFacade.handleLogin(userName, password);
        if (user == null) {
            return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(this.mainFacade.getUser(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/check-role")
    public ResponseEntity<String> checkStatus(@PathVariable("userId") String userId) {
        String role = this.mainFacade.getUserRole(userId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

}
