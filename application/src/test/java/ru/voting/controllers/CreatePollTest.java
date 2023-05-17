package ru.voting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.voting.common.Participant;
import ru.voting.common.Poll;
import ru.voting.common.PollAnswer;
import ru.voting.common.User;
import ru.voting.emails.EmailService;
import ru.voting.storage.DatabaseService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreatePollTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmailService emailService;

    @MockBean
    DatabaseService databaseService;


    @Test
    void testCreatePoll() throws Exception {
        PollAnswer ans1 = new PollAnswer();
        PollAnswer ans2 = new PollAnswer();

        Participant participant = new Participant();

        Poll poll = new Poll(
                "unique_id",
                "e@mail.ru",
                "Be or not to be?",
                Arrays.asList(ans1, ans2), List.of(participant),
                0
        );

        ans1.setAnswerText("one");
        ans2.setAnswerText("two");

        participant.setEmail("example@mail.ru");

        User user = new User(
                "e@mail.ru",
                "password",
                null
        );

        when(databaseService.getById(User.class, "e@mail.ru")).thenReturn(user);
        doNothing().when(databaseService).add(poll);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String pollJson = ow.writeValueAsString(poll);

        mockMvc.perform(post("/create_poll")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(pollJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Poll was created successfully"));
    }
}
