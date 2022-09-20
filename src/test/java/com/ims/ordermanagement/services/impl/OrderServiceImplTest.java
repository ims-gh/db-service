package com.ims.ordermanagement.services.impl;

import com.ims.ordermanagement.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {

    @SpyBean
    OrderServiceImpl orderService;

    @MockBean
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllOrders() {
    }

    @Test
    void getByOrderId() {
    }

    @Test
    void getByDeliveryLocation() {
    }

    @Test
    void getByDeliveryDate() {
        String date = "20-08-2022";
        orderService.getByDeliveryDate(date, "");

    }

    @Test
    void addNewOrder() {
    }

    @Test
    void updateOrder() {
    }

    @Test
    void deleteOrder() {
    }

    @Test
    void findOrThrowError() {
    }
}