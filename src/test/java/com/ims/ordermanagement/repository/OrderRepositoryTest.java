package com.ims.ordermanagement.repository;

import com.ims.ordermanagement.models.PaymentMethod;
import com.ims.ordermanagement.models.entities.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @SpyBean
    private OrderRepository testOrderRepository;

    Order order1;

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
    }

    @Test
    void testSaveOrder() {
        assertNotNull(testOrderRepository.save(order1));
    }
}