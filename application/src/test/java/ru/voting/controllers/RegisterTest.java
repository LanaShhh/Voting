package ru.voting.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import ru.voting.common.User;
import ru.voting.storage.DatabaseService;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private DatabaseService databaseService;

    @Test
    void testRegister() throws Exception {
        User newUser1 = new User();
        newUser1.setEmail("a@mail.com");
        newUser1.setPassword("12345");

        ObjectMapper mapper = new ObjectMapper();
        String newUserJson = mapper.writeValueAsString(newUser1);


        when(databaseService.getById(User.class, newUser1.getEmail())).thenReturn(null);
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(newUserJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Email " + newUser1.getEmail() + " successfully registered!"));

        when(databaseService.getById(User.class, newUser1.getEmail())).thenReturn(newUser1);
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(newUserJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User with such an email already exists"));
    }
}
