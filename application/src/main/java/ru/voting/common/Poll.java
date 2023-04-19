package ru.voting.common;

import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

@Data
public class Poll {
    public final String id;
    private final User creator;
    private final String question;
    @NonNull
    private Map<String, Integer> answers;
    private final List<String> participants;
    @NonNull
    private int answerCounter;
}
