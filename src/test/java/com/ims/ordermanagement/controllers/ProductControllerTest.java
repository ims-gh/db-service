package com.ims.ordermanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.ordermanagement.models.Category;
import com.ims.ordermanagement.models.dto.ProductDTO;
import com.ims.ordermanagement.models.entities.Product;
import com.ims.ordermanagement.services.impl.ProductServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    Product sixCupcakes;
    Product eightInchSingle;

    @BeforeEach
    void setUp() {
        sixCupcakes = new Product(
                "Box of 6 Cupcakes",
                "b6",
                55.0,
                "A box of 6 cupcakes with varied toppings",
                Category.CUPCAKES.name());
        eightInchSingle = new Product(
                "8 inch single layer cake",
                "8-inch-single",
                120.0,
                "8inch full cake",
                Category.FULL_CAKE.name());
    }

    @SneakyThrows
    @Test
    void getAllProducts() {
        List<Product> products = List.of(this.sixCupcakes, eightInchSingle);
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/products/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(2)))
                .andDo(print());
    }

    @SneakyThrows
    @Test
    void getProductBySlug() {
        when(productService.getProductBySlug("b6")).thenReturn(sixCupcakes);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/product?slug="+"b6")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("data", isA(HashMap.class)))
                .andDo(print());

    }

    @SneakyThrows
    @Test
    void addNewProduct() {
        ObjectMapper objectMapper = new ObjectMapper();
        productService.addNewProduct(eightInchSingle);
        objectMapper.findAndRegisterModules();
        String userJSON = objectMapper.writeValueAsString(eightInchSingle);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    void updateProduct() {
        productService.addNewProduct(eightInchSingle);
        String slug = "8-inch-single";
        ProductDTO productDTO = new ProductDTO(
                "8 inch single layer full cake",
                135.5);
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(productDTO);
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/product/" + slug)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void deleteProduct() {
        productService.addNewProduct(sixCupcakes);
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/product?slug=" + "b6")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}