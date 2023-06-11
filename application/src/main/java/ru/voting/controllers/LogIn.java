package ru.voting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.voting.common.User;
import ru.voting.storage.DatabaseService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class LogIn {
    @Autowired
    private DatabaseService databaseService;

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> logIn(@RequestBody User user) {
        User user1 = databaseService.getById(User.class, user.getEmail());
        if (user1 == null) {
            return new ResponseEntity<>("This email is not registered", HttpStatus.BAD_REQUEST);
        }
        if (!user1.getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
