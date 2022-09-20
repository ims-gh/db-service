package com.ims.ordermanagement.repository;

import com.ims.ordermanagement.models.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

//    Optional<OrderItem> findByOrderId(String uuid);

}