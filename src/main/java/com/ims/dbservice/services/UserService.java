package com.ims.dbservice.services;

import com.ims.dbservice.dto.UserDTO;
import com.ims.dbservice.models.User;
import com.ims.dbservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserByEmail(String email){
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new IllegalStateException("User with email " + email + " does not exist.");
    }

    public void addNewUser(User user) {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException(
                    "Account with email " + userOptional.get().getEmail() + " already exists.");
        }
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(String originalEmail, UserDTO userDTO) {
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String mobile = userDTO.getMobile();
        String email = userDTO.getEmail();
        String passwordHash = userDTO.getPasswordHash();
        String userRole = userDTO.getUserRole();
        LocalDateTime lastLogin = userDTO.getLastLogin();

        User user = userRepository.findUserByEmail(originalEmail)
                .orElseThrow(() -> new IllegalStateException(
                        "user with email " + originalEmail + "  does not exist"));

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
                throw new IllegalStateException("email taken");
            }
            user.setEmail(email);
        }
    }

    public void deleteUser(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (!userOptional.isPresent()) {
            throw new IllegalStateException("User does not exist");
        }
        userRepository.delete(userOptional.get());
    }



}
