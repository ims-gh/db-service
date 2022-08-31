package com.ims.dbservice.models.entities;

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
@Table(name = "order_item")
public class OrderItem {

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
    private Float price;
    @Column
    private Float finalPrice;
    @Column
    private Float discount;
    @Column
    private String image;
    @Column
    private String otherDetails;
    @Column
    private String orderId;  //TODO: add orderId as foreign key

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void setDiscount(Float discount) {
        this.discount = discount;
        this.finalPrice = this.price * discount;
    }
}
