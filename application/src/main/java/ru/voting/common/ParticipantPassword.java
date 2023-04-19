package ru.voting.common;

import lombok.Data;

@Data
public class ParticipantPassword {
    private final String password;
    private final String email;
    private final boolean isUsed;
    private final String pollId;
}
