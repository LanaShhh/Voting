package ru.voting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.voting.common.Participant;
import ru.voting.common.Poll;
import ru.voting.common.PollAnswer;
import ru.voting.storage.DatabaseService;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetPollTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    DatabaseService database;

    @Test
    void testGetPoll() throws Exception {
        PollAnswer a = new PollAnswer(null, "a", 0, null);
        PollAnswer b = new PollAnswer(null, "b", 0, null);

        Participant pA = new Participant(null, "e1", false, null);
        Participant pB = new Participant(null, "e2", false, null);

        Poll poll = new Poll(
                "unique_id",
                "email",
                "Be or not to be?",
                Arrays.asList(a, b), Arrays.asList(pA, pB),
                0
        );

        when(database.getById(Poll.class, "unique_id")).thenReturn(poll);

        mockMvc.perform(get("/get_poll?id=unique_id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(poll)));
    }
}
