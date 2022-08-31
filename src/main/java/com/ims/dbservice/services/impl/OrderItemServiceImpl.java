package com.ims.dbservice.services.impl;

import com.ims.dbservice.exceptions.OrderItemAlreadyExistsException;
import com.ims.dbservice.exceptions.OrderItemDoesNotExistException;
import com.ims.dbservice.models.dto.OrderItemDTO;
import com.ims.dbservice.models.entities.OrderItem;
import com.ims.dbservice.repository.OrderItemRepository;
import com.ims.dbservice.services.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> getAllOrderItems(){
        log.info("Getting all order items");
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getByOrderItemId(String uuid){
        log.info("Getting order with uuid {}", uuid);
        return findOrderItemOrThrowError(UUID.fromString(uuid));
    }

    @Override
    public String addNewOrderItem(OrderItem orderItem){
        log.info("Adding new orderItem: {}", orderItem);
//        throwsErrorIfOrderItemExists(orderItem.getOrderItemId());
        OrderItem saved = orderItemRepository.save(orderItem);
        return saved.getOrderItemId().toString();
    }

    @Override
    @Transactional
    public void updateOrderItem(String uuid, OrderItemDTO orderItemDTO) {
        log.info("Extracting order item details");
        String productSlug = orderItemDTO.getProductSlug();
        String inscription = orderItemDTO.getInscription();
        String colour = orderItemDTO.getColour();
        Float price = orderItemDTO.getPrice();
        Float discount = orderItemDTO.getDiscount();
        String image = orderItemDTO.getImage();
        String otherDetails = orderItemDTO.getOtherDetails();

        log.info("Finding orderItem with slug {}", uuid);
        OrderItem orderItem = findOrderItemOrThrowError(UUID.fromString(uuid));
        log.info("Updating orderItem");

        if (isNotNullOrEmptyOrBlank(productSlug) && !productSlug.equals(orderItem.getProductSlug())) {
            orderItem.setProductSlug(productSlug);
            log.info("OrderItem product slug updated successfully");
        }
        if (isNotNullOrEmptyOrBlank(inscription) && !inscription.equals(orderItem.getInscription())) {
            orderItem.setInscription(inscription);
            log.info("OrderItem inscription updated successfully");
        }
        if (isNotNullOrEmptyOrBlank(colour) && !colour.equals(orderItem.getColour())){
            orderItem.setColour(colour);
            log.info("OrderItem colour updated successfully");
        }
        if (!(price <= 0.0) && !price.equals(orderItem.getPrice())){
            orderItem.setPrice(price);
            log.info("OrderItem price updated successfully");
        }
        if ((discount != 0.0) && !discount.equals(orderItem.getDiscount())){
            orderItem.setDiscount(discount);
            log.info("OrderItem discount updated successfully");
        }
        if (isNotNullOrEmptyOrBlank(image) && !inscription.equals(orderItem.getImage())) {
            orderItem.setImage(image);
            log.info("OrderItem image updated successfully");
        }
        if (isNotNullOrEmptyOrBlank(otherDetails) && !otherDetails.equals(orderItem.getOtherDetails())){
            orderItem.setOtherDetails(otherDetails);
            log.info("OrderItem other details updated successfully");
        }
    }

    @Override
    public void deleteOrderItem(String uuid){
        log.info("deleting order item with id {}", uuid);
        OrderItem orderItem = findOrderItemOrThrowError(UUID.fromString(uuid));
        orderItemRepository.delete(orderItem);
    }

    private OrderItem findOrderItemOrThrowError(UUID uuid) {
        return orderItemRepository.findById(uuid)
                .orElseThrow(() -> new OrderItemDoesNotExistException(uuid.toString()));
    }

    private void throwsErrorIfOrderItemExists(UUID uuid) {
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(uuid);
        if (orderItemOptional.isPresent()) {
            throw new OrderItemAlreadyExistsException(uuid.toString());
        }
    }


}
