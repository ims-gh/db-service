package com.ims.dbservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.dbservice.dto.UserDTO;
import com.ims.dbservice.models.User;
import com.ims.dbservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.Matchers.*;
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
    @DisplayName("should get all users")
    void getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(ama);
        users.add(kojo);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(2)))
                .andDo(print());
    }

    @Test
    @DisplayName("should get user by email")
    void getUserByEmail() throws Exception {
        userService.addNewUser(kojo);
        when(userService.getUserByEmail("kojo@gmail.com")).thenReturn(kojo);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/"+"kojo@gmail.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", isA(HashMap.class)))
                .andDo(print());
    }

    @Test
    @DisplayName("should add new user")
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
    @DisplayName("should update existing user")
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
    @DisplayName("should delete user")
    void deleteUser() throws Exception {
        userService.addNewUser(kojo);
        userService.deleteUser("kojo@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + "kojo@gmail.com")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}