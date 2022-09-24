package com.ims.ordermanagement.models;

import com.ims.ordermanagement.models.entities.Order;
import com.ims.ordermanagement.models.entities.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderBody implements Serializable {

//    @JsonProperty(value = "order")
    private Order order;
    private List<OrderItem> orderItems = new ArrayList<>();

}
