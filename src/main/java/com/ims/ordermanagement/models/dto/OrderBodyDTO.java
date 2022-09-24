package com.ims.ordermanagement.models.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderBodyDTO implements Serializable {

    private OrderDTO order;
    private List<OrderItemDTO> orderItems = new ArrayList<>();

}
