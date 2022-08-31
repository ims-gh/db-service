package com.ims.dbservice.services;

import com.ims.dbservice.models.dto.OrderItemDTO;
import com.ims.dbservice.models.entities.OrderItem;

import java.util.List;

public interface OrderItemService extends DBService {

    public List<OrderItem> getAllOrderItems();

    public OrderItem getByOrderItemId(String uuid);

    public String addNewOrderItem(OrderItem orderItem);

    public void updateOrderItem(String uuid, OrderItemDTO orderItemDTO);

    public void deleteOrderItem(String uuid);



}
