package com.ims.dbservice.controllers;

import com.ims.dbservice.models.Category;
import com.ims.dbservice.models.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductController productController;

    Product sixCupcakes;
    Product eightInchSingle;

    @BeforeEach
    void setUp() {
        sixCupcakes = new Product(
                "Box of 6 Cupcakes",
                "b6",
                55.0,
                "A box of 6 cupcakes with varied toppings",
                Category.Cupcakes.name());
        eightInchSingle = new Product(
                "8 inch single layer cake",
                "8-inch-single",
                120.0,
                "8inch full cake",
                Category.FullCake.name());
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void getProductByName() {
    }

    @Test
    void addNewProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}