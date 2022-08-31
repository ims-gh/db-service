package com.ims.dbservice.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDTO {

    private String productSlug;
    private String inscription;
    private String colour;
    private Float price;
    private Float discount;
    private String image;
    private String otherDetails;


    public OrderItemDTO(String productSlug, Float price, Float discount) {
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
