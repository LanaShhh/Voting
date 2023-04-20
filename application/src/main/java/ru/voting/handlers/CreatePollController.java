package ru.voting.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.voting.common.Participant;
import ru.voting.common.Poll;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.voting.common.PollAnswer;
import ru.voting.common.User;
import ru.voting.emails.EmailService;
import ru.voting.storage.DatabaseService;
import ru.voting.storage.IdGenerator;

@RestController
public class CreatePollController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private DatabaseService database;

    @PostMapping(
            value = "/create_poll",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> createPoll(@RequestBody Poll newPoll) {
        // We get Poll with nullable id, so we need to set it
        newPoll.setPollId(idGenerator.generateNew(Poll.class));

        String creatorEmail = newPoll.getCreatorEmail();
        if (database.getById(User.class, creatorEmail) == null) {
            return new ResponseEntity<>(
                    "Only registered users can create polls",
                    HttpStatus.NOT_FOUND
            );
        }

        //TODO: add checking if user tries to add one poll for second time

        database.tryAddById(Poll.class, newPoll.getPollId(), newPoll);

        // Need to save answers to database
        for (PollAnswer pollAnswer : newPoll.getAnswers()) {
            String id = idGenerator.generateNew(PollAnswer.class);
            pollAnswer.setAnswerId(id);
            pollAnswer.setCounter(0);
            pollAnswer.setPollId(newPoll.getPollId());

            database.tryAddById(PollAnswer.class, id, pollAnswer);
        }

        for (Participant participant : newPoll.getParticipants()) {
            // We get participant with only email field set
            String password = idGenerator.generateNew(Participant.class);
            participant.setPassword(password);
            participant.setUsed(false);
            participant.setPollId(newPoll.getPollId());

            database.tryAddById(Participant.class, participant.getPassword(), participant);
            emailService.sendMessages(participant.getEmail(), participant);
        }

        return new ResponseEntity<>(
                "Poll was created successfully",
                HttpStatus.OK
        );
    }
}
