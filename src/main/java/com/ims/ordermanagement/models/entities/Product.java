package com.ims.ordermanagement.models.entities;

import com.ims.ordermanagement.models.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "products")
@NoArgsConstructor
public class Product implements DbEntity{

    @Id
    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String name;

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

    public void setPrice(Double price) {
        if (price > 0.0) {
            this.price = price;
        }
    }

    public Product(String name, String slug, Double price, String description, String category) {
        this.name = name;
        this.slug = slug;
        this.price = price;
        this.description = description;
        this.category = category;
    }


}
