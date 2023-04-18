package ru.voting.emails;

import ch.qos.logback.core.joran.sanity.Pair;
import org.springframework.stereotype.Service;
import ru.voting.common.ParticipantPassword;
import ru.voting.common.Poll;

import java.util.Queue;

@Service
public class EmailService {
    private Queue<Pair<String, String>> email_queue;
    private String service_email;

    private boolean sendEmail(Pair<String, String> email) {
        return true;
    }

    public void sendMessages(String email, ParticipantPassword parPass) {
        System.out.println("email service got request:");
        System.out.println(email + " " + parPass.getPassword());
    }
}
