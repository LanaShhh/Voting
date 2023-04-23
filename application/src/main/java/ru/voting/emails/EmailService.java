package ru.voting.emails;

import ch.qos.logback.core.joran.sanity.Pair;
import org.springframework.stereotype.Service;
import ru.voting.common.Participant;
import ru.voting.common.Poll;

import java.util.Queue;

@Service
public class EmailService {
    private Queue<Pair<String, String>> emailQueue;
    private String serviceEmail;

    private boolean sendEmail(Pair<String, String> email) {
        return true;
    }

    public void sendMessages(String email, Participant participant) {
        System.out.println("email service got request:");
        System.out.println(email + " " + participant.getPassword());
    }

    public void sendPollResults(Poll poll) {
        System.out.println("send result to " + poll.getCreatorEmail());
        System.out.println("poll " + poll.getPollId());
    }
}
