package com.ims.ordermanagement.services.interfaces;

import com.ims.ordermanagement.models.dto.UserDTO;
import com.ims.ordermanagement.models.entities.DbEntity;
import com.ims.ordermanagement.models.entities.User;

import java.util.List;

public interface UserService extends DBService {

    List<User> getAllUsers();

    User getUserByEmail(String email);

    void addNewUser(User user);

    void updateUser(String originalEmail, UserDTO userDTO);

    void deleteUser(String email);

    void throwsErrorIfExists(String value);

}
