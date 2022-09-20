package com.ims.ordermanagement.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements DtoObject {

    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String passwordHash;
    private String userRole;
    private LocalDateTime lastLogin;

    public UserDTO(String firstName, String lastName, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
    }
}
