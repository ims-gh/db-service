package com.ims.ordermanagement.services.impl;

import com.ims.ordermanagement.exceptions.OrderItemDoesNotExistException;
import com.ims.ordermanagement.models.dto.OrderItemDTO;
import com.ims.ordermanagement.models.entities.OrderItem;
import com.ims.ordermanagement.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Slf4j
class OrderItemServiceImplTest {

    @SpyBean
    private OrderItemServiceImpl orderItemService;

    @MockBean
    private OrderItemRepository orderItemRepository;

    OrderItem orderItem1;

    @BeforeEach
    void setUp() {
        orderItem1 = new OrderItem();
        orderItem1.setOrderItemId(UUID.randomUUID());
        orderItem1.setProductSlug("b6");
        orderItem1.setInscription("Happy birthday");
        orderItem1.setColour("purple");
        orderItem1.setPrice(80.0);
    }

    @Test
    void getAllOrderItems() {
        orderItemService.getAllOrderItems();
        verify(orderItemRepository).findAll();
    }

    @Test
    void getByOrderItemIdThrowsException() {
        String uuid = "67c81e54-2999-4293-8359-fd39bfa8ed41";
        assertThrows(OrderItemDoesNotExistException.class, () -> orderItemService.getByOrderItemId(uuid));
    }

    @Test
    void addNewOrderItem() {
        given(orderItemRepository.save(orderItem1)).willReturn(orderItem1);
        orderItemService.addNewOrderItem(orderItem1);
        ArgumentCaptor<OrderItem> orderItemArgumentCaptor = ArgumentCaptor.forClass(OrderItem.class);
        verify(orderItemRepository).save(orderItemArgumentCaptor.capture());
        OrderItem capturedOrderItem = orderItemArgumentCaptor.getValue();
        assertEquals(capturedOrderItem, orderItem1);
    }

    @Test
    void updateOrderItem() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setInscription("Happy birthday babe");
        given(orderItemRepository.findById(orderItem1.getOrderItemId()))
                .willReturn(Optional.of(orderItem1));

        orderItemService.updateOrderItem(orderItem1.getOrderItemId().toString(), orderItemDTO);
        assertEquals("Happy birthday babe", orderItem1.getInscription());
        assertEquals("purple", orderItem1.getColour());

    }

    @Test
    void deleteOrderItem() {
        given(orderItemRepository.findById(orderItem1.getOrderItemId()))
                .willReturn(Optional.of(orderItem1));
        orderItemService.deleteOrderItem(orderItem1.getOrderItemId().toString());
        verify(orderItemRepository).delete(orderItem1);

    }

    @Test
    void findOrThrowError() {
        UUID orderItemId = UUID.fromString("ac2736ca-8ab0-4667-b0cb-334cd74028c2");
        String errorMessage = "OrderItem with uuid fb2bbb70-1dc2-4644-9d61-b40364956865 does not exist.";
        assertThrows(OrderItemDoesNotExistException.class, () -> orderItemService.findOrThrowError(orderItemId), errorMessage);
        verify(orderItemRepository).findById(orderItemId);

    }
}