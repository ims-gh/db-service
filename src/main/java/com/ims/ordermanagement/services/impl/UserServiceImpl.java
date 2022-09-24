package com.ims.ordermanagement.services.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.ordermanagement.exceptions.UserAlreadyExistsException;
import com.ims.ordermanagement.exceptions.UserDoesNotExistException;
import com.ims.ordermanagement.models.dto.UserDTO;
import com.ims.ordermanagement.models.entities.User;
import com.ims.ordermanagement.repository.UserRepository;
import com.ims.ordermanagement.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("Finding user with email {}", email);
        return findOrThrowError(email);
    }

    @Override
    public void addNewUser(User user) {
        log.info("Adding new user: {}", user);
        throwsErrorIfExists(user.getEmail());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(String originalEmail, UserDTO userDTO) {
        String email = userDTO.getEmail();
        log.info("Finding user with email {}", originalEmail);
        User user = findOrThrowError(originalEmail);
        log.info("Updating user with email {}", originalEmail);

        if (isNotNullOrEmptyOrBlank(email) && !Objects.equals(user.getEmail(), email)) {
            throwsErrorIfExists(email);
            user.setEmail(email);
            userDTO.setEmail("");
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            mapper.updateValue(user, userDTO);
        } catch (JsonMappingException e) {
            log.error("Exception occurred when updating user with email {}", originalEmail, e);
        }
    }

    @Override
    public void deleteUser(String email) {
        log.info("Deleting user with email {}", email);
        userRepository.delete(findOrThrowError(email));
    }

    @Override
    public User findOrThrowError(Object email) {
        return userRepository.findUserByEmail((String) email)
                .orElseThrow(() -> new UserDoesNotExistException((String) email));
    }

    @Override
    public void throwsErrorIfExists(String email){
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException(email);
        }
    }

}
