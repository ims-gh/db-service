package com.ims.dbservice.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column
    private Long categoryId;

    @Column(nullable = false)
    private String categoryName;

    @Column
    private String description;
}
