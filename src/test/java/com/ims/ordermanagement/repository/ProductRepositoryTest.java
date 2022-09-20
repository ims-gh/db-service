package com.ims.ordermanagement.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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