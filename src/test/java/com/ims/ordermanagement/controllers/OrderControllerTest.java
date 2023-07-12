package com.ims.ordermanagement.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ims.ordermanagement.models.OrderBody;
import com.ims.ordermanagement.models.OrderStatus;
import com.ims.ordermanagement.models.PaymentMethod;
import com.ims.ordermanagement.models.dto.OrderBodyDTO;
import com.ims.ordermanagement.models.dto.OrderDTO;
import com.ims.ordermanagement.models.entities.Order;
import com.ims.ordermanagement.models.entities.OrderItem;
import com.ims.ordermanagement.services.impl.OrderServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl orderService;

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

    @SneakyThrows
    @Test
    void getAllOrdersTest() {
        List<Order> orders = List.of(order1);
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(1)))
                .andDo(print());
    }

    @SneakyThrows
    @Test
    void getOrderByIdTest() {
        when(orderService.getByOrderIds(List.of("1"))).thenReturn(List.of(order1));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/order?id="+order1.getOrderId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("data", isA(List.class)))
                .andDo(print());
    }

    @SneakyThrows
    @Test
    void addNewOrderTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String orderJSON = objectMapper.writeValueAsString(orderBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJSON))
                .andExpect(status().isCreated());
        
    }

    @SneakyThrows
    @Test
    void updateOrderTest() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);

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

        orderService.addNewOrder(orderBody);

        String orderJSON = mapper.writeValueAsString(updatedOrder1);
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/order/" + order1.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void deleteOrderTest() {
        orderService.addNewOrder(orderBody);
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/order?id=" + order1.getOrderId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}