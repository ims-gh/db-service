package com.ims.dbservice.services;

import com.ims.dbservice.models.dto.UserDTO;
import com.ims.dbservice.models.entities.User;

import java.util.List;

public interface UserService extends DBService {

    public List<User> getAllUsers();

    public User getUserByEmail(String email);

    public void addNewUser(User user);

    public void updateUser(String originalEmail, UserDTO userDTO);

    public void deleteUser(String email);
}
