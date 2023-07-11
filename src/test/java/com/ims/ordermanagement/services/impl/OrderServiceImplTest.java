package com.ims.ordermanagement.services.impl;

import com.ims.ordermanagement.exceptions.OrderDoesNotExistException;
import com.ims.ordermanagement.models.OrderBody;
import com.ims.ordermanagement.models.OrderStatus;
import com.ims.ordermanagement.models.PaymentMethod;
import com.ims.ordermanagement.models.dto.OrderBodyDTO;
import com.ims.ordermanagement.models.dto.OrderDTO;
import com.ims.ordermanagement.models.entities.Order;
import com.ims.ordermanagement.models.entities.OrderItem;
import com.ims.ordermanagement.repository.OrderItemRepository;
import com.ims.ordermanagement.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Slf4j
class OrderServiceImplTest {

    @SpyBean
    private OrderServiceImpl orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderItemRepository orderItemRepository;

    Order order1;
    OrderItem orderItem1;
    OrderBody orderBody;

    @BeforeEach
    void setUp() {
        order1 = new Order();
        order1.setOrderId("1");
        order1.setSessionId("2806231");
        order1.setBuyersName("Kofi");
        order1.setBuyersNumber("0201234567");
        order1.setBuyersSocial("ig@kofi_");
        order1.setRecipientsName("Ama");
        order1.setRecipientsNumber("0551234567");
        order1.setDeliveryLocation("Spintex");
        order1.setSpecificLocation("Shell signboard");
        order1.setGooglePlusCode("ABC123");
        order1.setDeliveryDate(LocalDateTime.of(2023, 6, 28, 15, 0));
        order1.setOrderStatus("unpaid");
        order1.setPaymentMethod(PaymentMethod.MOMO.name());
        order1.setTotal(80.0);

        orderItem1 = new OrderItem();
        orderItem1.setOrderItemId(UUID.randomUUID());
        orderItem1.setProductSlug("b6");
        orderItem1.setInscription("Happy birthday");
        orderItem1.setColour("purple");
        orderItem1.setPrice(80.0);

        orderBody = new OrderBody(order1, Arrays.asList(orderItem1));
    }

    @Test
    void getAllOrdersTest() {
        orderService.getAllOrders();
        verify(orderRepository).findAll();
    }

    @Test
    void addNewOrderTest() {
        given(orderItemRepository.saveAll(orderBody.getOrderItems())).willReturn(Arrays.asList(orderItem1));
        given(orderRepository.save(orderBody.getOrder())).willReturn(order1);
        String orderId = orderService.addNewOrder(orderBody);
        assertEquals("1", orderId);

        // Argument captor to check whether the argument received by the repository is the same as what was passed to the service

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order capturedOrder = orderArgumentCaptor.getValue();
        assertEquals(capturedOrder, order1);

        ArgumentCaptor<List<OrderItem>> orderItemArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(orderItemRepository).saveAll(orderItemArgumentCaptor.capture());
        List<OrderItem> capturedOrderItem = orderItemArgumentCaptor.getValue();
        assertEquals(capturedOrderItem, Arrays.asList(orderItem1));
    }

    @Test
    void getByOrderIdTest() {
        given(orderRepository.findAllById(List.of("1"))).willReturn(List.of(order1));
        assertEquals(List.of(order1), orderService.getByOrderIds(List.of("1")));
    }

    @Test
    void getByDeliveryLocationTest() {
        given(orderRepository.findByDeliveryLocationIn(List.of("Spintex"))).willReturn(Optional.of(List.of(order1)));
        assertEquals(List.of(order1), orderService.getByDeliveryLocations(List.of("Spintex")));
    }

    @Test
    void getByDeliveryLocationNotFoundTest() {
        assertEquals(new ArrayList<>(), orderService.getByDeliveryLocations(List.of("Taifa")));
    }

    @Test
    void getByDeliveryDateTest() {
        String startDate = "20-08-2022";
        String date = "20-08-2022";

        orderService.getByDeliveryDate(date, "");

    }


    @Test
    void updateOrder() {
        OrderDTO updatedOrder1 = new OrderDTO();
        updatedOrder1.setBuyersName("Kofi");
        updatedOrder1.setBuyersNumber("0201234567");
        updatedOrder1.setBuyersSocial("ig@kofi_");
        updatedOrder1.setRecipientsName("Ama");
        updatedOrder1.setRecipientsNumber("0551234567");
        updatedOrder1.setDeliveryLocation("Achimota");
        updatedOrder1.setSpecificLocation("Kingsby");
        updatedOrder1.setGooglePlusCode("123ABC");
        updatedOrder1.setDeliveryDate(LocalDateTime.of(2023, 6, 30, 15, 0));
        updatedOrder1.setOrderStatus(OrderStatus.PAID.name());
        updatedOrder1.setPaymentMethod(PaymentMethod.MOMO.name());
        updatedOrder1.setTotal(150.0);

        OrderBodyDTO orderBodyDTO = new OrderBodyDTO();
        orderBodyDTO.setOrderDTO(updatedOrder1);

        given(orderRepository.findById(order1.getOrderId()))
                .willReturn(Optional.of(order1));
        orderService.updateOrder(order1.getOrderId(), orderBodyDTO);
        assertAll(
                () -> assertEquals(updatedOrder1.getBuyersName(), order1.getBuyersName()),
                () -> assertEquals(updatedOrder1.getDeliveryLocation(), order1.getDeliveryLocation()),
                () -> assertEquals(updatedOrder1.getSpecificLocation(), order1.getSpecificLocation()),
                () -> assertEquals(updatedOrder1.getOrderStatus(), order1.getOrderStatus()),
                () -> assertEquals(updatedOrder1.getTotal(), order1.getTotal()),
                () -> assertEquals(updatedOrder1.getDeliveryDate(), order1.getDeliveryDate())
        );
    }

    @Test
    void updateOrderThrowsException() {
        String invalidId = "order123";
        OrderDTO updatedOrder = new OrderDTO();
        updatedOrder.setBuyersName("Kofi");
        updatedOrder.setBuyersNumber("0201234567");
        updatedOrder.setBuyersSocial("ig@kofi_");
        updatedOrder.setRecipientsName("Ama");
        updatedOrder.setRecipientsNumber("0551234567");
        updatedOrder.setDeliveryLocation("Achimota");
        updatedOrder.setSpecificLocation("Kingsby");
        updatedOrder.setGooglePlusCode("123ABC");
        updatedOrder.setDeliveryDate(LocalDateTime.of(2023, 6, 30, 15, 0));
        updatedOrder.setOrderStatus(OrderStatus.PAID.name());
        updatedOrder.setPaymentMethod(PaymentMethod.MOMO.name());
        updatedOrder.setTotal(150.0);

        OrderBodyDTO orderBodyDTO = new OrderBodyDTO();
        orderBodyDTO.setOrderDTO(updatedOrder);

        given(orderRepository.findById(invalidId))
                .willReturn(Optional.empty());
        String errorMessage = String.format("Order with id %s does not exist.", invalidId);
        assertThrows(OrderDoesNotExistException.class, () -> orderService.updateOrder(invalidId, orderBodyDTO), errorMessage);

    }

    @Test
    void deleteOrder() {
        given(orderRepository.findById(order1.getOrderId()))
                .willReturn(Optional.of(order1));
        orderService.deleteOrder(order1.getOrderId());
        verify(orderRepository).delete(order1);
    }

    @Test
    void deleteOrderThrowsException() {
        given(orderRepository.findById("order123"))
                .willReturn(Optional.empty());
        String errorMessage = "Order with id order123 does not exist.";
        assertThrows(OrderDoesNotExistException.class, () -> orderService.deleteOrder("order123"), errorMessage);

    }

    @Test
    void findOrThrowErrorNegative() {
        String orderId = "order098";
        String errorMessage = "Order with id order098 does not exist.";
        assertThrows(OrderDoesNotExistException.class, () -> orderService.findOrThrowError(orderId), errorMessage);
        verify(orderRepository).findById(orderId);

    }
}