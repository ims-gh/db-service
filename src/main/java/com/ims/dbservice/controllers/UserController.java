package com.ims.dbservice.controllers;

import com.ims.dbservice.dto.UserDTO;
import com.ims.dbservice.exceptions.ResponseHandler;
import com.ims.dbservice.models.User;
import com.ims.dbservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(userService.getAllUsers())
                .build();
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email){
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(userService.getUserByEmail(email))
                .build();
    }

    @PostMapping("/user")
    public ResponseEntity<Object> addNewUser(@RequestBody User user){
        userService.addNewUser(user);
        return ResponseHandler
                .builder()
                .status(HttpStatus.CREATED)
                .message("User successfully created.")
                .build();
    }

    @PutMapping("/user/{email}")
    public ResponseEntity<Object> updateUser(@PathVariable("email") String email,
                                             @RequestBody UserDTO userDTO){
        userService.updateUser(email, userDTO);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("User updated successfully.")
                .build();
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<Object> deleteUser(@PathVariable String email){
        userService.deleteUser(email);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("User deleted successfully.")
                .build();
    }


}
