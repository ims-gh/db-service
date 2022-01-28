package com.ims.dbservice.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column
    private Long orderId;

    @Column(nullable = false)
    private String sessionId;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private String buyersName;

    @Column(nullable = false)
    private String buyersNumber;

    @Column
    private String buyersSocial;

    @Column(nullable = false)
    private String recipientsName;

    @Column(nullable = false)
    private String recipientsNumber;

    @Column(nullable = false)
    private String deliveryLocation;

    @Column(nullable = false)
    private LocalDateTime deliveryDate;

    @Column(nullable = false)
    private String otherDetails;

    @Column(nullable = false)
    private Float discount;

    @Column(nullable = false)
    private Float subtotal;

    @Column(nullable = false)
    private Float total;

    @Column(nullable = false)
    private String orderStatus;

    @Column(nullable = false)
    private String paymentMethod;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
