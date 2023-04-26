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
import ru.voting.common.Poll;
import ru.voting.common.User;
import ru.voting.emails.EmailService;
import ru.voting.storage.DatabaseService;

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
    DatabaseService database;


    @Test
    void testCreatePoll() throws Exception {
        Poll poll = new Poll(
                "unique_id",
                "e@mail.ru",
                "Be or not to be?",
                null, null,
                0
        );

        User user = new User(
                "e@mail.ru",
                "password",
                null
        );

        when(database.getById(User.class, "e@mail.ru")).thenReturn(user);
        when(database.tryAddById(Poll.class, poll.getPollId(), poll)).thenReturn(true);


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
