package com.ims.dbservice.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column
    private Long orderItemId;
    @Column(nullable = false)
    private String productSlug;
    @Column
    private String inscription;
    @Column
    private String colour;
    @Column(nullable = false)
    private Float price;
    @Column
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


}
