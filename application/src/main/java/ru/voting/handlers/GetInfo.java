package ru.voting.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.voting.common.Poll;
import ru.voting.common.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class GetInfo {
    @Autowired
    private Map<String, Poll> polls;

    @Autowired
    private Map<String, User> users;

    @Autowired
    private Map<String, List<String>> usersPolls;

    @GetMapping("/get_info")
    public ResponseEntity<String> getInfo(@RequestParam String email) {
        if (!users.containsKey(email)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        List<Poll> pollsToReturn = new ArrayList<>();
        for (String pollId : usersPolls.get(email)) {
            pollsToReturn.add(polls.get(pollId));
        }
        return new ResponseEntity<>(pollsToReturn.toString(), HttpStatus.OK);
    }
}
