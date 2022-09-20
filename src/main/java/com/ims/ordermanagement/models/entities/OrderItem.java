package com.ims.ordermanagement.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem implements DbEntity {

    @Id
    @GeneratedValue
    private UUID orderItemId;
    @Column(nullable = false)
    private String productSlug;
    @Column
    private String inscription;
    @Column
    private String colour;
    @Column(nullable = false)
    private Double price;
    @Column
    private Double finalPrice;
    @Column
    private Double discount;
    @Column
    private String image;
    @Column
    private String otherDetails;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "orderId", nullable = false)
//    private Order order;  //TODO: add orderId as foreign key

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void setDiscount(Double discount) {
        this.discount = discount;
        this.finalPrice = (discount == 0.0) ? this.price : this.price * (1 - discount);
    }

}
