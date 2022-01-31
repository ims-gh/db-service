package com.ims.dbservice.services;

import com.ims.dbservice.dto.UserDTO;
import com.ims.dbservice.exceptions.UserAlreadyExistsException;
import com.ims.dbservice.exceptions.UserDoesNotExistException;
import com.ims.dbservice.models.User;
import com.ims.dbservice.repository.UserRepository;
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
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        log.info("Getting all users");
        return userRepository.findAll();
    }

    public User getUserByEmail(String email){
        log.info("Finding user with email {}", email);
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserDoesNotExistException(email);
    }

    public void addNewUser(User user) {
        log.info("Adding new user: {}", user);
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException(userOptional.get().getEmail());
        }
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(String originalEmail, UserDTO userDTO) {
        log.info("Extracting user details");
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String mobile = userDTO.getMobile();
        String email = userDTO.getEmail();
        String passwordHash = userDTO.getPasswordHash();
        String userRole = userDTO.getUserRole();
        LocalDateTime lastLogin = userDTO.getLastLogin();

        log.info("Finding user with email {}", originalEmail);
        User user = userRepository.findUserByEmail(originalEmail)
                .orElseThrow(() -> new UserDoesNotExistException(originalEmail));
        log.info("Updating user with email {}", email );
        if (firstName != null && firstName.length() > 0 && !Objects.equals(user.getFirstName(), firstName)) {
            user.setFirstName(firstName);
        }

        if (lastName != null && lastName.length() > 0 && !Objects.equals(user.getLastName(), lastName)) {
            user.setLastName(lastName);
        }

        if (mobile != null && mobile.length() > 0 && !Objects.equals(user.getMobile(), mobile)) {
            user.setMobile(mobile);
        }

        if (passwordHash != null && passwordHash.length() > 0 && !Objects.equals(user.getPasswordHash(), passwordHash)){
            user.setPasswordHash(passwordHash);
        }

        if (userRole != null && userRole.length() > 0 && !Objects.equals(user.getUserRole(), userRole)) {
            user.setUserRole(userRole);
        }

        if (lastLogin != null && lastLogin.isAfter(user.getLastLogin()) && !Objects.equals(user.getLastLogin(), lastLogin)){
            user.setLastLogin(lastLogin);
        }

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)){
            Optional<User> userOptional = userRepository
                    .findUserByEmail(email);
            if (userOptional.isPresent()){
                throw new UserAlreadyExistsException(email);
            }
            user.setEmail(email);
        }
    }

    public void deleteUser(String email) {
        log.info("Deleting user with email {}", email);
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UserDoesNotExistException(email);
        }
        userRepository.delete(userOptional.get());
    }



}
