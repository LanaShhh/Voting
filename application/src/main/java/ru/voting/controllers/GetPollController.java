package ru.voting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.voting.common.Participant;
import ru.voting.common.Poll;
import ru.voting.common.PollAnswer;
import ru.voting.emails.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.voting.storage.DatabaseService;

import java.util.ArrayList;
import java.util.List;


@RestController
public class GetPollController {
    @Autowired
    private DatabaseService databaseService;

    public static class getPollResponse {
        public String question;
        public List<String> answers = new ArrayList<>();

        public getPollResponse(Poll poll) {
            question = poll.getQuestion();
            for (PollAnswer pollAnswer : poll.getAnswers()) {
                answers.add(pollAnswer.getAnswerText());
            }
        }
    }

    @ResponseBody
    @GetMapping("/get_poll")
    public ResponseEntity<String> getPoll(@RequestParam String password) {
        if (password == null || password.equals("")) {
            return new ResponseEntity<>(
                    "Password is incorrect",
                    HttpStatus.BAD_REQUEST
            );
        }

        Participant participant = databaseService.getById(Participant.class, password);
        if (participant == null) {
            return new ResponseEntity<>(
                    "Participant not found",
                    HttpStatus.BAD_REQUEST
            );
        }

        Poll poll = databaseService.getById(Poll.class, participant.getPollId());

        try {
            getPollResponse response = new getPollResponse(poll);
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Serializing problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
