package ru.voting.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.voting.common.Poll;
import ru.voting.common.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GetInfo {
    @Autowired
    private Map<String, Poll> polls;

    @Autowired
    private Map<String, List<String>> usersPolls;

    @GetMapping("/get_info")
    public List<Poll> getInfo(@RequestParam User admin) {
        List<Poll> adminPolls = new ArrayList<>();
        for (String pollId : usersPolls.get(admin.getEmail())) {
            adminPolls.add(polls.get(pollId));
        }
        return adminPolls;
    }
}
