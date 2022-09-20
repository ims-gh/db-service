package com.ims.ordermanagement.services;

import com.ims.ordermanagement.models.dto.UserDTO;
import com.ims.ordermanagement.exceptions.UserAlreadyExistsException;
import com.ims.ordermanagement.exceptions.UserDoesNotExistException;
import com.ims.ordermanagement.models.entities.User;
import com.ims.ordermanagement.repository.UserRepository;
import com.ims.ordermanagement.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl testUserServiceImpl;

    String email;
    User user;

    @BeforeEach
    void setUp() {
        testUserServiceImpl = new UserServiceImpl(userRepository);
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
        testUserServiceImpl.getAllUsers();
        verify(userRepository).findAll();
    }

    @Test
    void addNewUser() {
        testUserServiceImpl.addNewUser(user);

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
        assertThrows(UserAlreadyExistsException.class, () -> testUserServiceImpl.addNewUser(user));
    }

    @Test
    void getUserByEmailTest() {
        given(userRepository.findUserByEmail(user.getEmail()))
                .willReturn(Optional.of(user));
        testUserServiceImpl.getUserByEmail(email);
        assertTrue(testUserServiceImpl.getUserByEmail(email).equals(user));
    }

    @Test
    void getUserByEmailThrowsException() {
        given(userRepository.findUserByEmail(user.getEmail()))
                .willReturn(Optional.empty());
        String errorMessage = "User with email " + email + " does not exist.";
        assertThrows(UserDoesNotExistException.class, () -> testUserServiceImpl.getUserByEmail(email), errorMessage);
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
        testUserServiceImpl.updateUser(email, updatedUser);
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
                        () -> testUserServiceImpl.updateUser(email, updatedUser), "user with email " + email + "  does not exist");

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
               () -> testUserServiceImpl.updateUser(email, updatedUser), "email taken");

    }


    @Test
    void deleteUserTest() {
        given(userRepository.findUserByEmail(user.getEmail()))
                .willReturn(Optional.of(user));
        testUserServiceImpl.deleteUser(email);
        verify(userRepository).delete(user);
    }


    @Test
    void deleteUserThrowsException() {
        given(userRepository.findUserByEmail(user.getEmail()))
                .willReturn(Optional.empty());
        String errorMessage = "User does not exist.";
        assertThrows(UserDoesNotExistException.class, () -> testUserServiceImpl.deleteUser(email), errorMessage);

    }
}