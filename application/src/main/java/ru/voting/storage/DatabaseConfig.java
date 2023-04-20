package ru.voting.storage;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.voting.common.Participant;
import ru.voting.common.Poll;
import ru.voting.common.PollAnswer;
import ru.voting.common.User;

@Configuration
public class DatabaseConfig {
    @Bean(name = "entityManagerFactory")
    public SessionFactory getEntityManagerFactory() {
        return new org.hibernate.cfg.Configuration()
                .addAnnotatedClass(Poll.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Participant.class)
                .addAnnotatedClass(PollAnswer.class)
                .buildSessionFactory();
    }
}
