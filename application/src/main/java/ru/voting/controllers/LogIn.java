package ru.voting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.voting.common.User;

import java.util.Map;

@Controller
public class LogIn {
    @Autowired
    private Map<String, User> users;

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> logIn(@RequestBody User user) {
        if (!users.containsKey(user.getEmail())) {
            return new ResponseEntity<>("This email is not registered", HttpStatus.BAD_REQUEST);
        }
        if (!users.get(user.getEmail()).getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
