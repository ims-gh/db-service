package com.ims.ordermanagement.models;

import com.ims.ordermanagement.models.entities.Order;
import com.ims.ordermanagement.models.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderBody implements Serializable {


    private Order order;
    private List<OrderItem> orderItems;

}
