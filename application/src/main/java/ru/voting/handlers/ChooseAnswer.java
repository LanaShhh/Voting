package ru.voting.handlers;

import lombok.NonNull;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.voting.common.ParticipantPassword;
import ru.voting.common.Poll;
import ru.voting.common.User;
import ru.voting.emails.EmailService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChooseAnswer {
    @Autowired
    private EmailService emailService;

    @Autowired
    private Map<String, Poll> polls;

    @Autowired
    private Map<String, List<String>> usersPolls;

    @Autowired
    private Map<String, ParticipantPassword> participantPasswords;

    @PutMapping(value = "/choose_answer")
    public void chooseAnswer(@RequestBody Map<String, String> paramMap) {
        String password = paramMap.get("password");
        String answer = paramMap.get("answer");
        ParticipantPassword curPassword = participantPasswords.get(password);
        System.out.println(
                "choose_answer, " +
                "password " + password +
                " answer " + answer +
                " is_used " + curPassword.isUsed() +
                " poll_id " + curPassword.getPollId());

        ParticipantPassword newPassword = new ParticipantPassword(
                curPassword.getPassword(),
                curPassword.getEmail(),
                true,
                curPassword.getPollId());
        participantPasswords.put(password, newPassword);

        Poll curPoll = polls.get(newPassword.getPollId());
        Map<String, Integer> newAnswers = new HashMap<>(curPoll.getAnswers());
        newAnswers.put(answer, newAnswers.get(answer) + 1);
        Poll newPoll = new Poll(
                curPoll.getId(),
                curPoll.getCreator(),
                curPoll.getQuestion(),
                newAnswers,
                curPoll.getParticipants(),
                curPoll.getAnswerCounter() + 1
                );
        polls.put(newPoll.getId(), newPoll);

        if (newPoll.getAnswerCounter() == newPoll.getAnswers().size()) {
            for (String participantPassword : newPoll.getParticipants()) {

            }
        }

    }
}
