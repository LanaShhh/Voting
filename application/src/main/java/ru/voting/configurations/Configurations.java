package ru.voting.configurations;

import ru.voting.common.Poll;
import ru.voting.common.Participant;
import ru.voting.common.User;
import ru.voting.emails.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.voting.storage.DatabaseService;

import java.util.*;

@Configuration
public class Configurations {
    @Bean
    public EmailService getEmailService() {
        return new EmailService();
    }
}
