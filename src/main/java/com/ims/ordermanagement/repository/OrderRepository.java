package com.ims.ordermanagement.repository;

import com.ims.ordermanagement.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<List<Order>> findByOrderDateBetween(LocalDateTime dateStart, LocalDateTime dateEnd);

    Optional<List<Order>> findByDeliveryLocationIn(List<String> locations);

    Optional<List<Order>> findByOrderStatusIn(List<String> statuses);

//    @Query("select o from Order o where o.deliveryDate between :start and :end")
    Optional<List<Order>> findByDeliveryDateBetween(LocalDateTime dateStart, LocalDateTime dateEnd);

    Optional<List<Order>> findByDeliveryDateBetweenAndOrderStatusIn(LocalDateTime dateStart, LocalDateTime dateEnd, List<String> statuses);

    Optional<List<Order>> findByOrderStatusInAndDeliveryLocationIn(List<String> status, List<String> location);

    Optional<List<Order>> findByBuyersName(String buyersName);

    Optional<List<Order>> findByRecipientsName(String recipientsName);

    Optional<List<Order>> findByPaymentMethod(String paymentMethod);
}
