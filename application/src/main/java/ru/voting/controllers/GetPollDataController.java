package ru.voting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import ru.voting.common.Participant;
import ru.voting.common.Poll;
import ru.voting.common.PollAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.voting.storage.DatabaseService;
import ru.voting.utility.Constants;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = Constants.frontendUrl)
@RestController
public class GetPollDataController {
    @Autowired
    private DatabaseService databaseService;

    public static class pollData {
        public String question;
        public List<String> answers = new ArrayList<>();

        public pollData(Poll poll) {
            question = poll.getQuestion();
            for (PollAnswer pollAnswer : poll.getAnswers()) {
                answers.add(pollAnswer.getAnswerText());
            }
        }
    }

    @ResponseBody
    @GetMapping("/get_poll_data")
    public ResponseEntity<String> getPollData(@RequestParam String password) {
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
            pollData response = new pollData(poll);
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(response), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Serializing problem", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
