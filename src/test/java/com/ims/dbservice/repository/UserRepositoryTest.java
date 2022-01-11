package com.ims.dbservice.repository;

import com.ims.dbservice.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository testUserRepository;

    @Test
    void findUserByEmailTest() {
        String email = "dzifa@gmail.com";
        User user = new User(
                "Dzifa",
                "Hodey",
                LocalDate.of(1990,05,23),
                "020334859",
                email,
                "hdhf",
                "admin"
        );

        testUserRepository.save(user);

        assertTrue(testUserRepository.findUserByEmail(email).isPresent());
    }
}