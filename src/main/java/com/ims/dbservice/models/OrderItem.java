package com.ims.dbservice.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column
    private Long orderItemId;

    @Column(nullable = false)
    private String inscription;

    @Column(nullable = false)
    private String colour;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Float discount;

    @Column
    private String image;

    @Column
    private String otherDetails;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}
