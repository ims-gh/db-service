package com.ims.ordermanagement.services.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.ordermanagement.exceptions.OrderItemDoesNotExistException;
import com.ims.ordermanagement.models.dto.OrderItemDTO;
import com.ims.ordermanagement.models.entities.OrderItem;
import com.ims.ordermanagement.repository.OrderItemRepository;
import com.ims.ordermanagement.services.interfaces.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> getAllOrderItems() {
        log.info("Getting all order items");
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getByOrderItemId(String uuid) {
        log.info("Getting order with uuid {}", uuid);
        return findOrThrowError(UUID.fromString(uuid));
    }

    @Override
    public String addNewOrderItem(OrderItem orderItem) {
        log.info("Adding new orderItem: {}", orderItem);
        OrderItem saved = orderItemRepository.save(orderItem);
        return saved.getOrderItemId().toString();
    }

    @Override
    @Transactional
    public void updateOrderItem(String uuid, OrderItemDTO orderItemDTO) {
        log.info("Finding orderItem with uuid {}", uuid);
        OrderItem orderItem = findOrThrowError(UUID.fromString(uuid));
        log.info("Updating orderItem {}", uuid);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            mapper.updateValue(orderItem, orderItemDTO);
        } catch (JsonMappingException e) {
            log.error("Exception occurred when updating order item with id {}", uuid, e);
        }
    }

    @Override
    public void deleteOrderItem(String uuid) {
        log.info("deleting order item with id {}", uuid);
        OrderItem orderItem = findOrThrowError(UUID.fromString(uuid));
        orderItemRepository.delete(orderItem);
    }

    @Override
    public OrderItem findOrThrowError(Object uuid) {
        return orderItemRepository.findById((UUID) uuid)
                .orElseThrow(() -> new OrderItemDoesNotExistException(uuid.toString()));
    }

}
