package ru.voting.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "polls")
@AllArgsConstructor
@NoArgsConstructor
public class Poll {
    @Id
    @Column(name = "poll_id")
    @Getter
    @Setter
    private String pollId;

    // Required
    @Column(name = "creator_email", nullable = false)
    @Getter
    @Setter
    private String creatorEmail;

    // Required
    @Column(name = "question", nullable = false)
    @Getter
    @Setter
    private String question;

    // Required
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id")
    @Getter
    @Setter
    private List<PollAnswer> answers;

    // Required
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id")
    @Getter
    @Setter
    private List<Participant> participants;

    @Column(name = "answer_counter", nullable = false)
    @Getter
    @Setter
    private int answerCounter;

    @Column(name = "result")
    @Getter
    @Setter
    private String result = null;
}
