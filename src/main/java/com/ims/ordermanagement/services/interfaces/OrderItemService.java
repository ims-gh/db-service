package com.ims.ordermanagement.services.interfaces;

import com.ims.ordermanagement.models.dto.OrderItemDTO;
import com.ims.ordermanagement.models.entities.OrderItem;

import java.util.List;

public interface OrderItemService extends DBService {

    List<OrderItem> getAllOrderItems();

    OrderItem getByOrderItemId(String uuid);

    String addNewOrderItem(OrderItem orderItem);

    void updateOrderItem(String uuid, OrderItemDTO orderItemDTO);

    void deleteOrderItem(String uuid);



}
