package com.ims.ordermanagement.repository;

import com.ims.ordermanagement.models.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @SpyBean
    private UserRepository testUserRepository;

    @Test
    void findUserByEmailTest() {
        String email = "dzifa@gmail.com";
        User user =User.builder()
                .firstName("Dzifa")
                .lastName("Hodey")
                .dob(LocalDate.of(1990,05,23))
                .email(email)
                .mobile("020334859")
                .passwordHash("hdhsamdklf")
                .userRole("admin")
                .build();

        testUserRepository.save(user);
        assertTrue(testUserRepository.findUserByEmail(email).isPresent());
    }
}