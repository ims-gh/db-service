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
@RequestMapping(path = "users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAllUsers(){
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(userService.getAllUsers())
                .build();
    }

    @GetMapping("{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email){
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(userService.getUserByEmail(email))
                .build();
    }

    @PostMapping
    public ResponseEntity<Object> addNewUser(@RequestBody User user){
        userService.addNewUser(user);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("User successfully created.")
                .build();
    }

    @PutMapping("{email}")
    public ResponseEntity<String> updateUser(@PathVariable("email") String email,
                                           @RequestBody UserDTO userDTO){
        userService.updateUser(email, userDTO);
        return ResponseEntity.ok().body("User updated successfully");
    }

    @DeleteMapping("{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email){
        userService.deleteUser(email);
        return ResponseEntity.ok().body("User deleted successfully.");
    }


}
