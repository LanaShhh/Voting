package ru.voting.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DbHandler {
    @Autowired
    private Map<String, Poll> polls;

    @Autowired
    private Map<String, List<String>> usersPolls;

    @Autowired
    private Map<String, ParticipantPassword> participantPasswords;

    public createNewPartici

}
