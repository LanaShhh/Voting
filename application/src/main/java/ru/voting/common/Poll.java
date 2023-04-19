package ru.voting.common;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Poll {
    private final String id;
    private final User creator;
    private final String question;
    private final Map<String, Integer> answers;
    private final List<String> participants;
    private final int answerCounter;
    private final List<Integer> results;
}
