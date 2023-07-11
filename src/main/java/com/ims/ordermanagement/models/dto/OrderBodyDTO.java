package com.ims.ordermanagement.models.dto;

import com.ims.ordermanagement.models.entities.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderBodyDTO implements Serializable {

    private OrderDTO orderDTO;
    private List<OrderItem> newOrderItems = new ArrayList<>();

}
