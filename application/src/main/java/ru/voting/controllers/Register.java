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
import ru.voting.storage.DatabaseService;

import java.util.Map;

@Controller
public class Register {
    @Autowired
    private DatabaseService databaseService;

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> register(@RequestBody User newUser) {
        User user1 = databaseService.getById(User.class, newUser.getEmail());
        if (user1 != null) {
            return new ResponseEntity<>("User with such an email already exists", HttpStatus.BAD_REQUEST);
        }
        databaseService.add(newUser);
        return new ResponseEntity<>("Email " + newUser.getEmail() + " successfully registered!", HttpStatus.OK);
    }
}
