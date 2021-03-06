package com.ims.dbservice.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name="users")
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column
    private Long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String userRole;

    @Column
    private LocalDateTime lastLogin;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    public User(String firstName, String lastName, LocalDate dob, String mobile, String email, String passwordHash, String userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.mobile = mobile;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userRole = userRole;
    }

    public User(String firstName, String lastName, LocalDate dob, String mobile, String email, String passwordHash, String userRole, LocalDateTime lastLogin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.mobile = mobile;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userRole = userRole;
        this.lastLogin = lastLogin;
    }

}

