package com.ims.ordermanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.ordermanagement.models.dto.OrderItemDTO;
import com.ims.ordermanagement.models.entities.OrderItem;
import com.ims.ordermanagement.services.impl.OrderItemServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemController.class)
class OrderItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private OrderItemServiceImpl orderItemService;

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

    @SneakyThrows
    @Test
    void getAllOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        when(orderItemService.getAllOrderItems()).thenReturn(orderItems);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/order-items/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(1)))
                .andDo(print());
    }

    @SneakyThrows
    @Test
    void getOrderItemById() {
        when(orderItemService.getByOrderItemId(any())).thenReturn(orderItem1);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/order-item?uuid="+orderItem1.getOrderItemId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("data", isA(LinkedHashMap.class)))
                .andDo(print());
    }

    @SneakyThrows
    @Test
    void addNewOrderItem() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String orderItemJSON = objectMapper.writeValueAsString(orderItem1);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/order-item/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderItemJSON))
                .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    void updateOrderItem() {
        orderItemService.addNewOrderItem(orderItem1);
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setInscription("Happy birthday babe");

        ObjectMapper objectMapper = new ObjectMapper();
        String orderItemJSON = objectMapper.writeValueAsString(orderItemDTO);
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/order-item/" + orderItem1.getOrderItemId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderItemJSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void deleteOrderItem() {
        orderItemService.addNewOrderItem(orderItem1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/order-item?uuid=" + orderItem1.getOrderItemId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}