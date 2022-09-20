package com.ims.ordermanagement.services.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.ordermanagement.exceptions.OrderDoesNotExistException;
import com.ims.ordermanagement.models.dto.OrderDTO;
import com.ims.ordermanagement.models.entities.Order;
import com.ims.ordermanagement.repository.OrderRepository;
import com.ims.ordermanagement.services.interfaces.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        log.info("Getting all order items");
        return orderRepository.findAll();
    }

    @Override
    public Order getByOrderIds(List<String> ids) {
        log.info("Getting order with id {}", ids);
        return findOrThrowError(ids);
    }

    @Override
    public List<Order> getByDeliveryLocations(List<String> location) {
        log.info("Getting orders with delivery location {}", location);
        return orderRepository.findByDeliveryLocationIn(location).orElse(new ArrayList<>());
    }

    @Override
    public List<Order> getByDeliveryDate(String dateStart, String dateEnd) {
        log.info("Getting orders with delivery date between {} and {}", dateStart, dateEnd);
        Pair<LocalDateTime, LocalDateTime> datePair = dateFormatter(dateStart, dateEnd);
        if (datePair != null) {
            LocalDateTime startDate = datePair.getFirst();
            LocalDateTime endDate = datePair.getSecond();
            return orderRepository.findByDeliveryDateBetween(startDate, endDate).orElse(new ArrayList<>());
        }
        log.info("Invalid delivery dates given: {} and {}", dateStart, dateEnd);
        return new ArrayList<>();
    }

    @Override
    public List<Order> getByOrderDate(String dateStart, String dateEnd) {
        log.info("Getting orders with order date between {} and {}", dateStart, dateEnd);
        Pair<LocalDateTime, LocalDateTime> datePair = dateFormatter(dateStart, dateEnd);
        if (datePair != null) {
            LocalDateTime startDate = datePair.getFirst();
            LocalDateTime endDate = datePair.getSecond();
            return orderRepository.findByOrderDateBetween(startDate, endDate).orElse(new ArrayList<>());
        }
        log.info("Invalid order dates given: {} and {}", dateStart, dateEnd);
        return new ArrayList<>();
    }

    @Override
    public List<Order> getByOrderStatuses(List<String> statuses) {
        log.info("Getting orders with status {}", statuses);
        List<String> statusValues = new ArrayList<>();
        statuses.forEach(status -> statusValues.add(Order.OrderStatus.getValue(status)));
        return orderRepository.findByOrderStatusIn(statusValues).orElse(new ArrayList<>());
    }

    @Override
    public String addNewOrder(Order order) {
        log.info("Adding new orderItem: {}", order);
        Order saved = orderRepository.save(order);
        return saved.getOrderId();
    }

    @Override
    @Transactional
    public void updateOrder(String id, OrderDTO orderDTO) {
        log.info("Finding order with id {}", id);
        Order order = findOrThrowError(id);
        log.info("Updating order {}", id);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            mapper.updateValue(order, orderDTO);
        } catch (JsonMappingException e) {
            log.error("Exception occurred when updating order with id {}", id, e);
        }
    }


    @Override
    public void deleteOrder(String id) {
        log.info("deleting order item with id {}", id);
        Order order = findOrThrowError(id);
        orderRepository.delete(order);
    }

    public static Pair<LocalDateTime,LocalDateTime> dateFormatter(String dateStart, String dateEnd){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.setLenient(Boolean.FALSE);
            LocalDateTime startDate = formatter.parse(dateStart).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime endDate = ((dateEnd.isBlank()) ? startDate : formatter.parse(dateEnd).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()).plusHours(23);
            return Pair.of(startDate, endDate);
        } catch (ParseException e) {
            log.error("Error while parsing dates. ", e);
            return null;
        }
    }

    @Override
    public Order findOrThrowError(Object id) {
        return orderRepository.findById((String) id)
                .orElseThrow(() -> new OrderDoesNotExistException((String) id));
    }
}
