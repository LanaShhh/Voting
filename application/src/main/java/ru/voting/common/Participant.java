package ru.voting.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participants",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "poll_id"})})
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    @Id
    @Column(name = "password")
    @Getter
    @Setter
    private String password;

    // Required
    @Column(name = "email", nullable = false)
    @Getter
    @Setter
    private String email;

    @Column(name = "is_used", nullable = false)
    @Getter
    @Setter
    private boolean isUsed;

    @Column(name = "poll_id", nullable = false)
    @Getter
    @Setter
    private String pollId;
}
