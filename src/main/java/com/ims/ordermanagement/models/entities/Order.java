package com.ims.ordermanagement.models.entities;

import com.ims.ordermanagement.config.OrderIdGenerator;
import com.ims.ordermanagement.models.OrderStatus;
import com.ims.ordermanagement.models.PaymentMethod;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order implements DbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @GenericGenerator(
            name = "order_seq",
            strategy = "com.ims.ordermanagement.config.OrderIdGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = OrderIdGenerator.INCREMENT_PARAM, value = "50")})
    private String orderId;

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

    @Column
    private String specificLocation;

    @Column
    private String googlePlusCode;

    @Column(nullable = false)
    private LocalDateTime deliveryDate;

    @Column
    private String otherDetails;

    @Column
    private Double discount = 0.0;

    @Column
    private Double subTotal = 0.0;

    @Column(nullable = false)
    private Double total = 0.0;

    @Column(nullable = false)
    private String orderStatus;

    @Column(nullable = false)
    private String paymentMethod;

    @CreationTimestamp
    private LocalDateTime orderDate;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItems(List<OrderItem> orderItemList) {
        orderItems.addAll(orderItemList);
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = OrderStatus.getValue(orderStatus);
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = PaymentMethod.getValue(paymentMethod);
    }


}
