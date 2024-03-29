package ru.voting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.voting.common.User;
import ru.voting.storage.DatabaseService;
import ru.voting.utility.Constants;

@CrossOrigin(origins = {"http://localhost:3000", Constants.frontendProdUrl})
@RestController
public class GetUserPollsController {
    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/get_user_polls")
    public ResponseEntity<String> getInfo(@RequestParam String email) {
        if (email == null || email.equals("")) {
            return new ResponseEntity<>(
                    "User email is incorrect",
                    HttpStatus.BAD_REQUEST);
        }

        User user = databaseService.getById(User.class, email);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(user.getPolls()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Serializing problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}