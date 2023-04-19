package ru.voting;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.voting.common.DbUser;

@SpringBootApplication
public class Application {
    public static SessionFactory sessionFactory;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @PostConstruct
    public void createSessionFactory() {
        System.out.println("Creating sessionFactory");
        sessionFactory = new Configuration()
                .addAnnotatedClass(DbUser.class)
                .buildSessionFactory();
    }
}
