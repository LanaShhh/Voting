package ru.voting.configurations;

import ru.voting.common.Poll;
import ru.voting.emails.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
}
