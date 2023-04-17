package ru.voting.configurations;

import ru.voting.common.Poll;
import ru.voting.common.ParticipantPassword;
import ru.voting.common.User;
import ru.voting.emails.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class Configurations {
    @Bean(name = "emailService")
    public EmailService getEmailService() {
        return new EmailService();
    }

    @Bean(name = "polls")
    public Map<String, Poll> getPolls() {
        return new HashMap<>();
    }

    @Bean(name = "users")
    public Set<User> getUsers() { return new HashSet<>(); }

    @Bean(name = "usersPolls")
    public Map<String, List<String>> getUsersPolls() { return new HashMap<>(); }

    @Bean(name = "participantPasswords")
    public Map<String, ParticipantPassword> getParticipantPasswords() {
        return new HashMap<>();
    }
}
