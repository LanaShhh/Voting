package ru.voting.common;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "participants")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Participant {
    @Id
    @Column(name = "password")
    @Getter @Setter
    private String password;

    @NonNull
    @Column(name = "email", nullable = false)
    @Getter @Setter
    private String email;

    @Column(name = "is_used", nullable = false)
    @Getter @Setter
    private boolean isUsed;

    @Column(name = "poll_id", nullable = false)
    @Getter @Setter
    private String pollId;
}
