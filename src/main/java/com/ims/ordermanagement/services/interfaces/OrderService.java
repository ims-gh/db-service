package com.ims.ordermanagement.services.interfaces;

import com.ims.ordermanagement.models.OrderBody;
import com.ims.ordermanagement.models.dto.OrderBodyDTO;
import com.ims.ordermanagement.models.entities.Order;

import java.util.List;

public interface OrderService extends DBService {

    List<Order> getAllOrders();

    List<Order> getByOrderIds(List<String> ids);

    List<Order> getByDeliveryDate(String dateStart, String dateEnd);

    List<Order> getByOrderDate(String dateStart, String dateEnd);

    List<Order> getByDeliveryLocations(List<String> location);

    List<Order> getByOrderStatuses(List<String> statuses);

    List<Order> getByDeliveryDateAndOrderStatuses(String dateStart, String dateEnd, List<String> statuses);

    List<Order> getByOrderStatusesAndDeliveryLocations(List<String> statuses, List<String> locations);

    List<Order> getByBuyersName(String buyersName);

    List<Order> getByRecipientsName(String recipientsName);

    List<Order> getByPaymentMethod(String paymentMethod);

    String addNewOrder(OrderBody orderBody);

    void updateOrder(String id, OrderBodyDTO orderBodyDTO);

    void deleteOrder(String id);





}
