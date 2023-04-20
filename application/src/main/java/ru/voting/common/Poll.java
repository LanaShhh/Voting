package ru.voting.common;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "polls")
@AllArgsConstructor
@NoArgsConstructor
public class Poll {
    @Id
    @Column(name = "poll_id")
    @Getter @Setter
    private String pollId;

    @Column(name = "creator_email", nullable = false)
    @Getter @Setter
    private String creatorEmail;

    @Column(name = "question", nullable = false)
    @Getter @Setter
    private String question;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "poll_id")
    @Getter
    private List<PollAnswer> answers;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "poll_id")
    @Getter
    private List<Participant> participants;

    @Column(name = "answer_counter", nullable = false)
    @Getter @Setter
    private int answerCounter;
}
