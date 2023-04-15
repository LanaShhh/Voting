package ru.voting.emails;

import ch.qos.logback.core.joran.sanity.Pair;
import org.springframework.stereotype.Service;
import ru.voting.common.Poll;

import java.util.Queue;

@Service
public class EmailService {
    private Queue<Pair<String, String>> email_queue;
    private String service_email;

    private boolean sendEmail(Pair<String, String> email) {
        throw new RuntimeException("Not implemented");
    }

    public void sendMessages(Poll poll) {
        throw new RuntimeException("Not implemented");
    }
}
