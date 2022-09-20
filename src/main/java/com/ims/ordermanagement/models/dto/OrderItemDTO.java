package com.ims.ordermanagement.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO implements DtoObject {

    private String productSlug;
    private String inscription;
    private String colour;
    private Double price;
    private Double discount;
    private String image;
    private String otherDetails;


    public OrderItemDTO(String productSlug, Double price, Double discount) {
        this.productSlug = productSlug;
        this.price = price;
        this.discount = discount;
    }

    public OrderItemDTO(String inscription, String colour, String image, String otherDetails) {
        this.inscription = inscription;
        this.colour = colour;
        this.image = image;
        this.otherDetails = otherDetails;
    }
}
