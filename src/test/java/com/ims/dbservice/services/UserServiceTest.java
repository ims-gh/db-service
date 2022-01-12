package com.ims.dbservice.services;

import com.ims.dbservice.dto.UserDTO;
import com.ims.dbservice.exceptions.UserAlreadyExistsException;
import com.ims.dbservice.exceptions.UserDoesNotExistException;
import com.ims.dbservice.models.User;
import com.ims.dbservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService testUserService;

    String email;
    User user;

    @BeforeEach
    void setUp() {
        testUserService = new UserService(userRepository);
        email = "hello@gmail.com";
        user = new User(
                "hello",
                "Hodey",
                LocalDate.of(1990,05,23),
                "020334859",
                email,
                "hdhf",
                "admin",
                LocalDateTime.of(2021,12,30,11,20,10)
        );
    }

    @Test
    void getAllUsersTest() {
        testUserService.getAllUsers();
        verify(userRepository).findAll();
    }

    @Test
    void addNewUser() {
        testUserService.addNewUser(user);

        // Argument captor is used to check
        // whether the argument received by the userRepository is the same as what was passed to the userService

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser, user);
    }

    @Test
    void addUserTestThrowsException() {
        given(userRepository.findUserByEmail(user.getEmail()))
                .willReturn(Optional.of(user));
        assertThrows(UserAlreadyExistsException.class, () -> testUserService.addNewUser(user));
    }

    @Test
    void getUserByEmailTest() {
        given(userRepository.findUserByEmail(user.getEmail()))
                .willReturn(Optional.of(user));
        testUserService.getUserByEmail(email);
        assertTrue(testUserService.getUserByEmail(email).equals(user));
    }

    @Test
    void getUserByEmailThrowsException() {
        given(userRepository.findUserByEmail(user.getEmail()))
                .willReturn(Optional.empty());
        String errorMessage = "User with email " + email + " does not exist.";
        assertThrows(UserDoesNotExistException.class, () -> testUserService.getUserByEmail(email), errorMessage);
    }

    @Test
    void updateUserTest() {
        UserDTO updatedUser = new UserDTO(
                "Dzifs",
                "Lo",
                "02036665334",
                "hi@gmail.com",
                "password123",
                "customer",
                LocalDateTime.of(2022,01,10,14,30,05));

        given(userRepository.findUserByEmail(user.getEmail()))
                .willReturn(Optional.of(user));
        testUserService.updateUser(email, updatedUser);
        assertAll(
                () -> assertTrue(user.getFirstName().equals(updatedUser.getFirstName())),
                () -> assertTrue(user.getLastName().equals(updatedUser.getLastName())),
                () -> assertTrue(user.getMobile().equals(updatedUser.getMobile())),
                () -> assertTrue(user.getEmail().equals(updatedUser.getEmail())),
                () -> assertTrue(user.getPasswordHash().equals(updatedUser.getPasswordHash())),
                () -> assertTrue(user.getUserRole().equals(updatedUser.getUserRole())),
                () -> assertTrue(user.getLastLogin().isEqual(updatedUser.getLastLogin()))
                );
    }

    @Test
    void updateUserThrowsNotExistsException(){
        UserDTO updatedUser = new UserDTO(
                "Dzifs",
                "Hodey",
                "02036665334",
                email,
                "password",
                "admin",
                LocalDateTime.of(2022,01,10,14,30,05));

        given(userRepository.findUserByEmail(email))
                .willReturn(Optional.empty());
        assertThrows(UserDoesNotExistException.class,
                        () -> testUserService.updateUser(email, updatedUser), "user with email " + email + "  does not exist");

    }

    @Test
    void updateUserThrowsEmailException(){
        String newEmail = "ho@gmail.com";
        UserDTO updatedUser = new UserDTO(
                "Dzifs",
                "Hodey",
                "02036665334",
                newEmail,
                "password",
                "admin",
                LocalDateTime.of(2022,01,10,14,30,05));

        User newUser = new User(
                "ho",
                "ho",
                LocalDate.of(1990,05,23),
                "02056334859",
                newEmail,
                "hdhf",
                "admin",
                LocalDateTime.of(2021,12,30,11,20,10)
        );

        given(userRepository.findUserByEmail(email))
                .willReturn(Optional.of(user));
        given(userRepository.findUserByEmail(newEmail))
                .willReturn(Optional.of(newUser));
       assertThrows(UserAlreadyExistsException.class,
               () -> testUserService.updateUser(email, updatedUser), "email taken");

    }


    @Test
    void deleteUserTest() {
        given(userRepository.findUserByEmail(user.getEmail()))
                .willReturn(Optional.of(user));
        testUserService.deleteUser(email);
        verify(userRepository).delete(user);
    }


    @Test
    void deleteUserThrowsException() {
        given(userRepository.findUserByEmail(user.getEmail()))
                .willReturn(Optional.empty());
        String errorMessage = "User does not exist.";
        assertThrows(UserDoesNotExistException.class, () -> testUserService.deleteUser(email), errorMessage);

    }
}