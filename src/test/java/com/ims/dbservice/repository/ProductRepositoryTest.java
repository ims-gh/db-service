package com.ims.dbservice.repository;

import com.ims.dbservice.models.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository testProductRepository;

    /*@Test
    @DisplayName("should find saved category by name")
    void findByCategoryNameTest() {
        String name = "baking materials";
        Category category = new Category(
                name,
                "raw materials");

        testProductRepository.save(category);
        assertTrue(testProductRepository.findByProductName(name).isPresent());

    }*/
}