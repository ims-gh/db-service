package com.ims.dbservice.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Data
@Entity
@Table(name="users")
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

    @Transient
    private Integer age;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String userRole;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    public User() {
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }


    }

