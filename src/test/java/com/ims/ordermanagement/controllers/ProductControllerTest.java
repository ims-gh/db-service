package com.ims.ordermanagement.controllers;

import com.ims.ordermanagement.models.entities.Product;
import com.ims.ordermanagement.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @SpyBean
    private ProductController productController;

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
                Product.Category.CUPCAKES.name());
        eightInchSingle = new Product(
                "8 inch single layer cake",
                "8-inch-single",
                120.0,
                "8inch full cake",
                Product.Category.FULL_CAKE.name());
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