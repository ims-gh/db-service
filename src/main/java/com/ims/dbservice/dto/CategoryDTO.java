package com.ims.dbservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTO {
    private String categoryName;
    private String description;

    public CategoryDTO(String categoryName) {
        this.categoryName = categoryName;
    }
}