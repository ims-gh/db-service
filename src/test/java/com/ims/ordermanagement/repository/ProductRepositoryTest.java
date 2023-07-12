package com.ims.ordermanagement.repository;

import com.ims.ordermanagement.models.Category;
import com.ims.ordermanagement.models.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ProductRepositoryTest {

    @SpyBean
    private ProductRepository testProductRepository;

    Product sixCupcakes;

    @BeforeEach
    void setUp() {
        sixCupcakes = new Product(
                "Box of 6 Cupcakes",
                "b6",
                55.0,
                "A box of 6 cupcakes with varied toppings",
                Category.CUPCAKES.name());
    }

    @Test
    void addNewProductTest() {
        assertNotNull(testProductRepository.save(sixCupcakes));
    }
}