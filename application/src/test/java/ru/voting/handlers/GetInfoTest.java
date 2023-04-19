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

import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.boot.context.properties.bind.Bindable.mapOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetInfoTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    Map<String, Poll> polls;

    @MockBean
    Map<String, User> users;

    @MockBean
    Map<String, Set<String>> usersPolls;

    @Test
    void testGetInfo() throws Exception {
        User eleazar = new User(
                "e@mail.ru",
                "qwerty123"
        );

        Poll poll1 = new Poll(
                "id_1",
                eleazar,
                "Be or not to be?",
                Map.of("be", 0,
                        "do not be", 0),
                Arrays.asList("a@b.ru", "c@d.ru"),
                0
        );
        Poll poll2 = new Poll(
                "id_2",
                eleazar,
                "Tea or coffey?",
                Map.of("tea", 0,
                        "coffey", 0),
                Arrays.asList("a@b.ru", "x@d.ru"),
                0
        );

        when(users.containsKey(eq("e@mail.ru"))).thenReturn(true);
        when(polls.get(eq("id_1"))).thenReturn(poll1);
        when(polls.get(eq("id_2"))).thenReturn(poll2);
        when(usersPolls.get(eq("e@mail.ru"))).thenReturn(
                Set.of("id_1", "id_2")
        );

        mockMvc.perform(get("/get_info?email=e@mail.ru"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        Arrays.asList(poll1, poll2).toString()
                ));
    }

}
