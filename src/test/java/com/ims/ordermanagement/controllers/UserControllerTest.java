package com.ims.ordermanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.ordermanagement.models.dto.UserDTO;
import com.ims.ordermanagement.models.entities.User;
import com.ims.ordermanagement.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    private String email;
    private User ama;
    private User kojo;

    @BeforeEach
    void setUp() {
        email = "hello@gmail.com";
        ama = User.builder()
                .firstName("Ama")
                .lastName("Hodey")
                .dob(LocalDate.of(1990,05,23))
                .email("ama@gmail.com")
                .mobile("020334859")
                .passwordHash("hdhsamdklf")
                .userRole("admin")
                .build();
        kojo =User.builder()
                .firstName("Kojo")
                .lastName("Kay")
                .dob(LocalDate.of(1990,05,23))
                .email("kojo@gmail.com")
                .mobile("020334859")
                .passwordHash("hdhsamdklf")
                .userRole("admin")
                .build();

    }

    @Test
    @DisplayName("should get all users")
    void getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(ama);
        users.add(kojo);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/")
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
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user?email="+"kojo@gmail.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("data", isA(HashMap.class)))
                .andDo(print());
    }

    @Test
    @DisplayName("should add new user")
    void addNewUserTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
               userService.addNewUser(kojo);
        objectMapper.findAndRegisterModules();
        String userJSON = objectMapper.writeValueAsString(kojo);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user/")
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
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/user/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should delete user")
    void deleteUser() throws Exception {
        userService.addNewUser(kojo);
        userService.deleteUser("kojo@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user?email=" + "kojo@gmail.com")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}