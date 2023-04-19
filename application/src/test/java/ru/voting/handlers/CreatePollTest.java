package ru.voting.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.voting.common.ParticipantPassword;
import ru.voting.common.Poll;
import ru.voting.common.User;
import ru.voting.emails.EmailService;

import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
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
    Map<String, Poll> polls;

    @MockBean
    Map<String, User> users;

    @MockBean
    Map<String, Set<String>> usersPolls;

    @MockBean
    Map<String, ParticipantPassword> participantPasswords;

    @Test
    void testCreatePoll() throws Exception {
        Poll poll = new Poll(
                "unique_id",
                new User(
                        "e@mail.ru",
                        "qwerty123"
                ),
                "Be or not to be?",
                Map.of("be", 0,
                        "do not be", 0),
                Arrays.asList("a@b.ru", "c@d.ru"),
                0
        );

        when(users.containsKey(eq("e@mail.ru"))).thenReturn(true);
        when(usersPolls.get("e@mail.ru")).thenReturn(new HashSet<>());

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
