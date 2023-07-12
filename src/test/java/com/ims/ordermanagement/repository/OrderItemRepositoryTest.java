package com.ims.ordermanagement.repository;

import com.ims.ordermanagement.models.entities.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderItemRepositoryTest {

    @SpyBean
    private OrderItemRepository testOrderItemRepository;

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
    void testSaveOrderItem() {
        assertNotNull(testOrderItemRepository.saveAll(Arrays.asList(orderItem1)));
    }
}