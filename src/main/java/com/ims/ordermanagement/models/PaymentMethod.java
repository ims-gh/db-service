package com.ims.ordermanagement.models;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum PaymentMethod {
    MOMO,
    CASH,
    BANK,
    UNKNOWN;

    public static String getValue(String paymentMethod) {
        return Arrays.stream(PaymentMethod.values())
                .filter(method -> method.name().equalsIgnoreCase(paymentMethod))
                .findFirst()
                .orElse(PaymentMethod.UNKNOWN)
                .name();
    }
}