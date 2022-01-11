package com.ims.dbservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String passwordHash;
    private String userRole;
    private LocalDateTime lastLogin;
}
