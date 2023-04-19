package ru.voting.handlers;

import org.springframework.web.bind.annotation.RestController;
import ru.voting.common.Poll;
import ru.voting.emails.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RestController
public class GetPollController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private Map<String, Poll> polls;


    @GetMapping("/get_poll")
    public ResponseEntity<String> getPoll(@RequestParam String id) {
        if (!polls.containsKey(id)) {
            return new ResponseEntity<>("Id not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(polls.get(id).toString(), HttpStatus.OK);
        }
    }
}
