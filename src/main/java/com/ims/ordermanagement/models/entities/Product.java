package com.ims.ordermanagement.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "products")
@NoArgsConstructor
public class Product implements DbEntity{

    @Id
    @GeneratedValue
    private UUID productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private Double price;

    @Column
    private String description;

    @Column
    private String category;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void setCategory(String category) {
        this.category = Category.getValue(category);
    }

    public Product(String name, String slug, Double price, String description, String category) {
        this.name = name;
        this.slug = slug;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    @AllArgsConstructor
    public enum Category {
        FULL_CAKE,
        CUPCAKES,
        CAKE_SLICES,
        EXTRAS,
        UNKNOWN;

        public static String getValue(String category) {
            return Arrays.stream(Category.values())
                    .filter(cat -> cat.name().equalsIgnoreCase(category))
                    .findFirst()
                    .orElse(Category.UNKNOWN)
                    .name();
        }
    }

}
