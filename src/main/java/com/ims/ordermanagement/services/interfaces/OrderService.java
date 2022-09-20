package com.ims.ordermanagement.services.interfaces;

import com.ims.ordermanagement.models.dto.OrderDTO;
import com.ims.ordermanagement.models.entities.Order;

import java.util.List;

public interface OrderService extends DBService {

    public List<Order> getAllOrders();

    public Order getByOrderIds(List<String> ids);

    public List<Order> getByDeliveryDate(String dateStart, String dateEnd);

    public List<Order> getByOrderDate(String dateStart, String dateEnd);

    public List<Order> getByDeliveryLocations(List<String> location);

    public List<Order> getByOrderStatuses(List<String> statuses);

    public String addNewOrder(Order order);

    public void updateOrder(String id, OrderDTO orderDTO);

    public void deleteOrder(String id);





}
