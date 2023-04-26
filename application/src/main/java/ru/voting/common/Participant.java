package ru.voting.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participants")
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
