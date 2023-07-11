package com.ims.ordermanagement.models;

import lombok.AllArgsConstructor;

import java.util.Arrays;

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