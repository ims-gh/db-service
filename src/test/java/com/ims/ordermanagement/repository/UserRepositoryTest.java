package com.ims.ordermanagement.repository;

import com.ims.ordermanagement.models.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
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