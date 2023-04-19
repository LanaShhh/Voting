package ru.voting.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import ru.voting.common.ParticipantPassword;
import ru.voting.common.Poll;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.voting.common.User;
import ru.voting.emails.EmailService;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

@Controller
public class CreatePoll {
    @Autowired
    private EmailService emailService;

    @Autowired
    private Map<String, Poll> polls;

    @Autowired
    private Map<String, User> users;

    @Autowired
    private Map<String, Set<String>> usersPolls;

    @Autowired
    private Map<String, ParticipantPassword> participantPasswords;

    final String PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghij" +
            "klmnopqrstuvwxyz0123456789~`!@" +
            "#$%^&*()-_=+[{]}\\|;:'\",<.>/?";

    @PostMapping(
            value = "/create_poll",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> createPoll(@RequestBody Poll newPoll) {
        String creator_email = newPoll.getCreator().getEmail();
        if (!users.containsKey(creator_email)) {
            return new ResponseEntity<>(
                    "Only registered users can create polls",
                    HttpStatus.NOT_FOUND
            );
        }

        String newPollId = newPoll.getId();
        polls.put(newPollId, newPoll);
        usersPolls.get(creator_email).add(newPollId);

        for (String participantEmail : newPoll.getParticipants()) {
            String password = RandomStringUtils.random(12, PASSWORD_CHARACTERS);
            while (participantPasswords.containsKey(password)) {
                password = RandomStringUtils.random(12, PASSWORD_CHARACTERS);
            }
            ParticipantPassword parPass = new ParticipantPassword(password,
                    false, newPollId);
            participantPasswords.put(password, parPass);
            emailService.sendMessages(participantEmail, parPass);
        }

        return new ResponseEntity<>(
                "Poll was created successfully",
                HttpStatus.OK
        );
    }
}
