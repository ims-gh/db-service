package com.ims.dbservice.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
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
