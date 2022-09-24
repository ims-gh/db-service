package com.ims.ordermanagement.models.entities;

import com.ims.ordermanagement.config.OrderIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    public void setDiscount(Double discount) {
        this.discount = discount;
        setTotal();
    }

    public void setTotal() {
        this.total = (this.discount == 0.0) ? this.subTotal : this.subTotal * (1 - this.discount);
    }

    public void setSubTotal(List<OrderItem> orderItemList) {
        orderItemList.forEach(item -> this.subTotal += item.getFinalPrice());
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = OrderStatus.getValue(orderStatus);
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = PaymentMethod.getValue(paymentMethod);
    }

    @AllArgsConstructor
    public enum OrderStatus {
        UNPAID,
        PAID,
        DELIVERED,
        PICKED_UP,
        CANCELLED,
        UNKNOWN;

        public static String getValue(String status) {
            return Arrays.stream(OrderStatus.values())
                    .filter(orderStatus -> orderStatus.name().equalsIgnoreCase(status))
                    .findFirst()
                    .orElse(OrderStatus.UNKNOWN)
                    .name();
        }
    }

    @AllArgsConstructor
    public enum PaymentMethod {
        MOMO,
        CASH,
        BANK,
        UNKNOWN;

        public static String getValue(String paymentMethod) {
            return Arrays.stream(PaymentMethod.values())
                    .filter(method -> method.name().equalsIgnoreCase(paymentMethod))
                    .findFirst()
                    .orElse(PaymentMethod.UNKNOWN)
                    .name();
        }
    }
}
