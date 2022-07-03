package com.ims.dbservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String name;
    private Double price = 0.0;
    private String description;
    private String category;

    public ProductDTO(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public ProductDTO(Double price) {
        this.price = price;
    }

    public ProductDTO(String name) {
        this.name = name;
    }

    public ProductDTO(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public ProductDTO(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
}
