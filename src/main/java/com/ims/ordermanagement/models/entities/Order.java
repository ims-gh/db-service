package com.ims.ordermanagement.models.entities;

import com.ims.ordermanagement.config.OrderIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;


@Getter
@Setter
@Entity
@Table(name = "orders")
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
    private LocalDateTime orderDate;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    @OneToMany(mappedBy = "order")
//    private List<OrderItem> orderItems;

    public void setDiscount(Double discount) {
        this.discount = discount;
        this.total = (discount == 0.0) ? this.subtotal : this.subtotal * (1 - discount);
    }

    @AllArgsConstructor
    public enum OrderStatus{
        UNPAID,
        PAID,
        DELIVERED,
        CANCELLED,
        UNKNOWN;

        public static String getValue(String status){
            return Arrays.stream(OrderStatus.values())
                    .filter(orderStatus -> orderStatus.name().equalsIgnoreCase(status))
                    .findFirst()
                    .orElse(OrderStatus.UNKNOWN)
                    .name();
        }
    }
}
