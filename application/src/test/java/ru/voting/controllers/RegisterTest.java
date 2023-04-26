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
    private Map<String, User> users;

    @Test
    void testRegister() throws Exception {
        User newUser1 = new User("email@mail.com", "12345");

        ObjectMapper mapper = new ObjectMapper();
        String newUserJson = mapper.writeValueAsString(newUser1);


        when(users.containsKey(eq(newUser1.getEmail()))).thenReturn(false);
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(newUserJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Email " + newUser1.getEmail() + " successfully registered!"));

        when(users.containsKey(eq(newUser1.getEmail()))).thenReturn(true);
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(newUserJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User with such an email already exists"));
    }
}
