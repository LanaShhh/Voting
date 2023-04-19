package ru.voting.handlers;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import ru.voting.common.Poll;
import ru.voting.common.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.boot.context.properties.bind.Bindable.mapOf;
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
    Map<String, Poll> polls;

    @Test
    void testGetPoll() throws Exception {
        Poll poll = new Poll(
                "unique_id",
                new User(
                        "email",
                        "password"
                ),
                "Be or not to be?",
                Map.of("be", 0,
                        "do not be", 1),
                Arrays.asList("a@b.ru", "c@d.ru"),
                0,
                Arrays.asList(0, 0)
        );

        when(polls.containsKey(eq("unique_id"))).thenReturn(true);
        when(polls.get(eq("unique_id"))).thenReturn(poll);

        mockMvc.perform(get("/get_poll?id=unique_id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(poll.toString()));
    }
}
