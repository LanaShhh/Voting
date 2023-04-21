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
@Table(name = "poll_answers")
@NoArgsConstructor
@AllArgsConstructor
public class PollAnswer {
    @Id
    @Column(name = "answer_id")
    @Getter
    @Setter
    private String answerId;

    // Required
    @Column(name = "answer_text", nullable = false)
    @Getter
    @Setter
    private String answerText;

    @Column(name = "counter", nullable = false)
    @Getter
    @Setter
    private int counter;

    @Column(name = "poll_id", nullable = false)
    @Getter
    @Setter
    private String pollId;
}
