package com.ims.ordermanagement.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements DtoObject{

    private String orderId;
    private String buyersName;
    private String buyersNumber;
    private String buyersSocial;
    private String recipientsName;
    private String recipientsNumber;
    private String deliveryLocation;
    private LocalDateTime deliveryDate;
    private String otherDetails;
    private Double discount;
    private Double subtotal;
    private Double total;
    private String orderStatus;
    private String paymentMethod;

}
