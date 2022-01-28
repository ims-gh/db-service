package com.ims.dbservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.dbservice.dto.UserDTO;
import com.ims.dbservice.models.User;
import com.ims.dbservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private String email;
    private User ama;
    private User kojo;

    @BeforeEach
    void setUp() {
        email = "hello@gmail.com";
        ama = new User(
                "Ama",
                "Hodey",
                LocalDate.of(1990, 05, 23),
                "020334859",
                email,
                "hdhf",
                "admin",
                LocalDateTime.of(2021, 12, 30, 11, 20, 10)
        );
        kojo = new User(
                "Kojo",
                "Kay",
                LocalDate.of(1990, 05, 23),
                "0203342349",
                "kojo@gmail.com",
                "hdhf",
                "user",
                LocalDateTime.of(2021, 12, 30, 11, 20, 10, 23)
        );

    }

    @Test
    void getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(ama);
        users.add(kojo);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void addNewUserTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        kojo = new User(
                "Kojo",
                "Kay",
                LocalDate.of(1990, 05, 23),
                "0203342349",
                "kojo@gmail.com",
                "hdhf",
                "user"
        );

        userService.addNewUser(kojo);
        objectMapper.findAndRegisterModules();
        String userJSON = objectMapper.writeValueAsString(kojo);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO updatedAma = new UserDTO(
                "Amar", "Owusu", "020374839");

        userService.addNewUser(ama);
        userService.updateUser(email, updatedAma);

        String userJSON = objectMapper.writeValueAsString(updatedAma);
        mockMvc.perform(MockMvcRequestBuilders.put("/user/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        userService.addNewUser(kojo);
        userService.deleteUser("kojo@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + "kojo@gmail.com")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}