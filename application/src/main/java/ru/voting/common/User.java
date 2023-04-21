package ru.voting.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "email", nullable = false)
    @Getter
    @Setter
    private String email;

    @Column(name = "password", nullable = false)
    @Getter
    @Setter
    private String password;

    // USE ONLY WHEN GET USER FROM DATABASE, ELSE NULL
    // TODO: NEED TO CHECK IT IN LOG IN
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_email")
    @Getter
    @Setter
    private List<Poll> polls;
}
