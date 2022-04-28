package com.fit3077.covidtestingregistration.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fit3077.covidtestingregistration.Operator;

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

    private Operator operator;

    public ActionController() {
        operator = new Operator();
    }

    @PostMapping("/login")
    public ResponseEntity<JsonNode> login(@RequestParam("userName") String userName,
            @RequestParam("password") String password) {
        this.operator.createLogin(userName, password);
        JsonNode userNode = new ObjectMapper().convertValue(this.operator.getUser(),
                JsonNode.class);
        if (userNode == null) {
            return new ResponseEntity<>(userNode, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(userNode, HttpStatus.OK);
        }
    }

    @PostMapping("/onsite-booking")
    public ResponseEntity<Void> onsiteBooking(@RequestBody ObjectNode userObject) {
        boolean successful = this.operator.getUser().handleBooking(userObject);
        if (successful) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check-status")
    public ResponseEntity<String> checkStatus(@RequestParam("pin") String pin) {
        String status = this.operator.getUser().checkStatus(pin);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
