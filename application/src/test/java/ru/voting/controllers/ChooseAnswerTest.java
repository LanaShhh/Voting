package ru.voting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.voting.common.Participant;
import ru.voting.common.Poll;
import ru.voting.common.PollAnswer;
import ru.voting.common.User;
import ru.voting.emails.EmailService;
import ru.voting.storage.DatabaseService;

import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChooseAnswerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmailService emailService;

    @MockBean
    DatabaseService databaseService;

    @Test
    void chooseAnsTest() throws Exception {
        PollAnswer ans1 = new PollAnswer();
        PollAnswer ans2 = new PollAnswer();

        Participant participant = new Participant();

        Poll poll = new Poll(
                "1",
                "e@mail.ru",
                "Be or not to be?",
                Arrays.asList(ans1, ans2), Arrays.asList(participant),
                0,
                null
        );

        ans1.setAnswerText("one");
        ans1.setAnswerId("ans1");
        ans1.setCounter(0);
        ans1.setPollId("1");

        ans2.setAnswerText("two");
        ans2.setAnswerId("ans1");
        ans2.setCounter(0);
        ans2.setPollId("1");

        participant.setEmail("example@mail.ru");
        participant.setPassword("111");
        participant.setUsed(false);
        participant.setPollId("1");

        User user = new User(
                "e@mail.ru",
                "password",
                null
        );

        String password = "111";

        when(databaseService.getById(Participant.class, password)).thenReturn(participant);
        doNothing().when(databaseService).update(participant);
        when(databaseService.getById(Poll.class, participant.getPollId())).thenReturn(poll);
        doNothing().when(databaseService).update(poll);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String pollJson = ow.writeValueAsString(poll);

        mockMvc.perform(put("/choose_answer?password=" + password + "&answer=" + ans1.getAnswerText()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("Set answer successfully"));
    }
}
