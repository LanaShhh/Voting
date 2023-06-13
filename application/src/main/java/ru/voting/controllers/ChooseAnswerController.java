package ru.voting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.voting.common.Participant;
import ru.voting.common.Poll;
import ru.voting.emails.EmailService;
import ru.voting.storage.DatabaseService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ChooseAnswerController {
    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private EmailService emailService;

    @PutMapping(value = "/choose_answer")
    private ResponseEntity<String> chooseAnswer(@RequestParam String password, @RequestParam String answer) {
        if (password == null || password.equals("")) {
            return new ResponseEntity<>(
                    "Password is incorrect",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (answer == null || answer.equals("")) {
            return new ResponseEntity<>(
                    "Answer is incorrect",
                    HttpStatus.BAD_REQUEST
            );
        }

        synchronized (this) {
            Participant participant = databaseService.getById(Participant.class, password);

            if (participant == null) {
                return new ResponseEntity<>(
                        "Participant not found",
                        HttpStatus.BAD_REQUEST
                );
            }

            if (participant.isUsed()) {
                return new ResponseEntity<>(
                        "Password has been used",
                        HttpStatus.BAD_REQUEST
                );
            }

            participant.setUsed(true);
            databaseService.update(participant);

            Poll poll = databaseService.getById(Poll.class, participant.getPollId());

            poll.setAnswerCounter(poll.getAnswerCounter() + 1);

            for (int i = 0; i < poll.getAnswers().size(); i++) {
                if (poll.getAnswers().get(i).getAnswerText().equals(answer)) {
                    poll.getAnswers().get(i).setCounter(poll.getAnswers().get(i).getCounter() + 1);
                    break;
                }
            }

            if (poll.getAnswerCounter() == poll.getParticipants().size()) {
                poll.setResult(databaseService.getResult(poll));
                emailService.sendPollResults(poll);
            }

            databaseService.update(poll);
        }

        return new ResponseEntity<>(
                "Set answer successfully",
                HttpStatus.OK
        );
    }

}
