package com.ims.dbservice.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String buyersName;

    @Column(nullable = false)
    private String buyersNumber;

    @Column
    private String buyersSocial;

    @Column
    private String recipientsName;

    @Column
    private String recipientsNumber;

    @Column(nullable = false)
    private String deliveryLocation;

    @Column(nullable = false)
    private LocalDateTime deliveryDate;

    @Column
    private String otherDetails;

    @Column
    private Double discount = 0.0;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private String orderStatus;

    @Column(nullable = false)
    private String paymentMethod;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime orderDate;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // TODO: SORT OUT THIS RELATIONSHIP
//    @Column
//    private List<String> orderItemId;

}
