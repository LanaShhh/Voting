package ru.voting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.voting.common.Poll;
import ru.voting.common.User;

import java.util.Map;

@Controller
public class Register {
    @Autowired
    private Map<String, User> users;

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> register(@RequestBody User newUser) {
        if (users.containsKey(newUser.getEmail())) {
            return new ResponseEntity<>("User with such an email already exists", HttpStatus.BAD_REQUEST);
        }
        users.put(newUser.getEmail(), newUser);
        return new ResponseEntity<>("Email " + newUser.getEmail() + " successfully registered!", HttpStatus.OK);
    }
}
