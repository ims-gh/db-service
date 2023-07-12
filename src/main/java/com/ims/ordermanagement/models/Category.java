package com.ims.ordermanagement.models;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum Category {
    FULL_CAKE,
    CUPCAKES,
    CAKE_SLICES,
    TOPPERS,
    TOPPINGS,
    DRINKS,
    BALLOONS,
    EXTRAS,
    UNKNOWN;

    public static String getValue(String category) {
        return Arrays.stream(Category.values())
                .filter(cat -> cat.name().equalsIgnoreCase(category))
                .findFirst()
                .orElse(Category.UNKNOWN)
                .name();
    }
}
