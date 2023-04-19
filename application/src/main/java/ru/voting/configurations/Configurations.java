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
    @Bean
    public EmailService getEmailService() {
        return new EmailService();
    }

    @Bean(name = "polls")
    public Map<String, Poll> getPolls() {
        return new HashMap<>();
    }

    @Bean(name = "users")
    public Map<String, User> getUsers() { return new HashMap<>(); }

    @Bean(name = "usersPolls")
    public Map<String, Set<String>> getUsersPolls() { return new HashMap<>(); }

    @Bean(name = "participantPasswords")
    public Map<String, ParticipantPassword> getParticipantPasswords() {
        return new HashMap<>();
    }
}
