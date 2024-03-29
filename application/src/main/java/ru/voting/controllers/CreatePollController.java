package ru.voting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.voting.common.Participant;
import ru.voting.common.Poll;
import ru.voting.common.PollAnswer;
import ru.voting.common.User;
import ru.voting.emails.EmailService;
import ru.voting.emails.PollNotifier;
import ru.voting.storage.DatabaseService;
import ru.voting.storage.IdGenerator;
import ru.voting.utility.Constants;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = {"http://localhost:3000", Constants.frontendProdUrl})
@RestController
public class CreatePollController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private PollNotifier pollNotifier;

    @PostMapping(
            value = "/create_poll",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> createPoll(@RequestBody Poll newPoll) {
        // Check that we have all required fields
        String isOk = checknewPoll(newPoll);
        if (isOk != null) {
            return new ResponseEntity<>(
                    isOk,
                    HttpStatus.BAD_REQUEST
            );
        }

        synchronized (this) {
            // We get Poll with nullable id, so we need to set it
            newPoll.setPollId(idGenerator.generateNew(Poll.class));

            String creatorEmail = newPoll.getCreatorEmail();
            if (databaseService.getById(User.class, creatorEmail) == null) {
                return new ResponseEntity<>(
                        "Only registered users can create polls",
                        HttpStatus.NOT_FOUND
                );
            }

            //TODO: add checking if user tries to add one poll for second time

            // Fill answer fields that we do not get from form
            for (PollAnswer pollAnswer : newPoll.getAnswers()) {
                String id = idGenerator.generateNew(PollAnswer.class);
                pollAnswer.setAnswerId(id);
                pollAnswer.setCounter(0);
                pollAnswer.setPollId(newPoll.getPollId());
            }

            for (Participant participant : newPoll.getParticipants()) {
                // We get participant with only email field set
                String password = idGenerator.generateNew(Participant.class);
                participant.setPassword(password);
                participant.setUsed(false);
                participant.setPollId(newPoll.getPollId());
            }

            databaseService.add(newPoll);
        }

        pollNotifier.notifyPollParticipants(newPoll);

        return new ResponseEntity<>(
                "Poll was created successfully",
                HttpStatus.OK
        );
    }

    public String checknewPoll(Poll newPoll) {
        if (newPoll == null) {
            return "Poll is empty";
        }
        if (newPoll.getCreatorEmail() == null || newPoll.getCreatorEmail().equals("")) {
            return "Email is incorrect";
        }
        if (newPoll.getQuestion() == null || newPoll.getQuestion().equals("")) {
            return "Question is incorrect";
        }
        if (newPoll.getAnswers() == null || newPoll.getAnswers().size() < 2) {
            return "Need more answers variants";
        }
        for (PollAnswer answer : newPoll.getAnswers()) {
            if (answer.getAnswerText() == null || answer.getAnswerText().equals("")) {
                return "Incorrect answer variant";
            }
        }
        if (newPoll.getParticipants() == null || newPoll.getParticipants().size() == 0) {
            return "Need more participants";
        }
        for (Participant participant : newPoll.getParticipants()) {
            if (participant.getEmail() == null || participant.getEmail().equals("")) {
                return "Incorrect participant email";
            }
        }

        Set<String> answersSet = new HashSet<>();
        for (PollAnswer ans : newPoll.getAnswers()) {
            answersSet.add(ans.getAnswerText());
        }
        if (answersSet.size() != newPoll.getAnswers().size()) {
            return "Answers must be unique";
        }

        Set<String> participantsSet = new HashSet<>();
        for (Participant p : newPoll.getParticipants()) {
            participantsSet.add(p.getEmail());
        }
        if (participantsSet.size() != newPoll.getParticipants().size()) {
            return "Participants' emails must be unique";
        }

        return null;
    }
}
