package ru.voting.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.voting.common.Poll;
import ru.voting.common.User;
import ru.voting.storage.DatabaseService;

import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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
    DatabaseService database;

    @Test
    void testGetInfo() throws Exception {
        User eleazar = new User(
                "e@mail.ru",
                "qwerty123",
                null
        );

        Poll poll1 = new Poll(
                "id_1",
                eleazar.getEmail(),
                "Be or not to be?",
                null, null,
                0
        );
        Poll poll2 = new Poll(
                "id_2",
                eleazar.getEmail(),
                "Tea or coffey?",
                null, null,
                0
        );

        eleazar.setPolls(Arrays.asList(poll1, poll2));

        when(database.getById(User.class, "e@mail.ru")).thenReturn(eleazar);

        mockMvc.perform(get("/get_info?email=e@mail.ru"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        Arrays.asList(poll1, poll2).toString()
                ));
    }

}
