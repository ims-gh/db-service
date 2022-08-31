package com.ims.dbservice.controllers;

import com.ims.dbservice.models.dto.UserDTO;
import com.ims.dbservice.exceptions.ResponseHandler;
import com.ims.dbservice.models.entities.User;
import com.ims.dbservice.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "v1")
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(userServiceImpl.getAllUsers())
                .build();
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUserByEmail(@RequestParam String email){
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(userServiceImpl.getUserByEmail(email))
                .build();
    }

    @PostMapping("/user")
    public ResponseEntity<Object> addNewUser(@RequestBody User user){
        userServiceImpl.addNewUser(user);
        return ResponseHandler
                .builder()
                .data(user.getEmail())
                .status(HttpStatus.CREATED)
                .message("User successfully created.")
                .build();
    }

    @PutMapping("/user/{email}")
    public ResponseEntity<Object> updateUser(@PathVariable("email") String email,
                                             @RequestBody UserDTO userDTO){
        userServiceImpl.updateUser(email, userDTO);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("User updated successfully.")
                .build();
    }

    @DeleteMapping("/user")
    public ResponseEntity<Object> deleteUser(@RequestParam String email){
        userServiceImpl.deleteUser(email);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("User deleted successfully.")
                .build();
    }


}
