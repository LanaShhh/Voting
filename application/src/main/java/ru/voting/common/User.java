package ru.voting.common;

import lombok.Data;

@Data
public class User {
    private final String email;
    private final String password;
}
