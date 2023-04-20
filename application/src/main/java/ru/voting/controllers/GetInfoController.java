package ru.voting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.voting.common.User;
import ru.voting.storage.DatabaseService;

@Controller
public class GetInfoController {
    @Autowired
    private DatabaseService databaseService;
    @GetMapping("/get_info")
    public ResponseEntity<String> getInfo(@RequestParam String email) {
        User user = databaseService.getById(User.class, email);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.getPolls().toString(), HttpStatus.OK);
    }
}
