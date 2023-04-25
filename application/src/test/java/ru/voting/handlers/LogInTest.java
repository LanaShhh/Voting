package ru.voting.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.voting.common.User;

import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LogInTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private Map<String, User> users;

    @Test
    void testLogIn() throws Exception {
        User user1 = new User("a@mail.com", "12345");
        User user2 = new User("test@mail.com", "66666");

        ObjectMapper mapper = new ObjectMapper();
        String user1Json = mapper.writeValueAsString(user1);
        String user2Json = mapper.writeValueAsString(user2);

        when(users.containsKey(eq(user1.getEmail()))).thenReturn(true);
        when(users.get(user1.getEmail())).thenReturn(user1);
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(user1Json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

        when(users.containsKey(eq(user2.getEmail()))).thenReturn(false);
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(user2Json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("This email is not registered"));


    }
}
