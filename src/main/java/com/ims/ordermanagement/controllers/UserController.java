package com.ims.ordermanagement.controllers;

import com.ims.ordermanagement.models.dto.UserDTO;
import com.ims.ordermanagement.exceptions.ResponseHandler;
import com.ims.ordermanagement.models.entities.User;
import com.ims.ordermanagement.services.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "v1")
@Api(value = "order-management-system")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @ApiOperation(value = "Get a list of all users")
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(userServiceImpl.getAllUsers())
                .build();
    }

    @ApiOperation(value = "Get a user by email")
    @GetMapping("/user")
    public ResponseEntity<Object> getUserByEmail(@RequestParam String email){
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(userServiceImpl.getUserByEmail(email))
                .build();
    }

    @ApiOperation(value = "Add a new user")
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

    @ApiOperation(value = "Update an existing user")
    @PatchMapping("/user/{email}")
    public ResponseEntity<Object> updateUser(@PathVariable("email") String email,
                                             @RequestBody UserDTO userDTO){
        userServiceImpl.updateUser(email, userDTO);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(userDTO)
                .message("User updated successfully.")
                .build();
    }

    @ApiOperation(value = "Delete an existing user")
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
