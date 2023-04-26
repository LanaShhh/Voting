package ru.voting.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.voting.emails.EmailService;

@Configuration
public class Configurations {
    @Bean
    public EmailService getEmailService() {
        return new EmailService();
    }
}
