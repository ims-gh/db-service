package com.ims.ordermanagement.services;

import com.ims.ordermanagement.models.dto.ProductDTO;
import com.ims.ordermanagement.exceptions.ProductAlreadyExistsException;
import com.ims.ordermanagement.exceptions.ProductDoesNotExistException;
import com.ims.ordermanagement.models.entities.Product;
import com.ims.ordermanagement.repository.ProductRepository;
import com.ims.ordermanagement.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

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
    @DisplayName("should get all products")
    void getAllProductsTest() {
        List<Product> products = List.of(eightInchSingle, sixCupcakes);
        when(productRepository.findAll())
                .thenReturn(products);

        List<Product> allProducts = productServiceImpl.getAllProducts();
        assertAll(
                () -> assertEquals(2, allProducts.size()),
                () -> assertTrue(allProducts.get(1).equals(sixCupcakes)),
                () -> assertEquals("FULL_CAKE", allProducts.get(0).getCategory())
        );
    }

    @Nested
    @DisplayName("should add new product")
    class addNewProduct {
        @Captor
        private ArgumentCaptor<Product> productArgumentCaptor;

        @Test
        @DisplayName("given a valid product")
        void addNewProductTest() {
            Product sixInchDouble = new Product(
                    "6 inch double layer cake",
                    "6-inch-double",
                    140.0,
                    "6inch full cake",
                    Product.Category.FULL_CAKE.name()
            );
            productServiceImpl.addNewProduct(sixInchDouble);
            verify(productRepository).save(productArgumentCaptor.capture());

            Product capturedSixInch = productArgumentCaptor.getValue();
            assertAll(
                    () -> assertEquals(sixInchDouble, capturedSixInch),
                    () -> assertEquals("6 inch double layer cake", capturedSixInch.getName())
            );
        }

        @Test
        @DisplayName("given product already exists, throws exception")
        void addNewProductThrowsException() {
            when(productRepository.findBySlug("8-inch-single"))
                    .thenReturn(Optional.of(eightInchSingle));
            assertThrows(
                    ProductAlreadyExistsException.class,
                    () -> productServiceImpl.addNewProduct(eightInchSingle));
        }
    }

    @Nested
    @DisplayName("should find product by slug")
    class getProductByName {
        @Test
        @DisplayName("given a valid/existing product")
        void getProductBySlugTest() {
            String productSlug = "8-inch-single";
            when(productRepository.findBySlug(productSlug))
                    .thenReturn(Optional.of(eightInchSingle));
            Product testProduct = productServiceImpl.getProductBySlug(productSlug);
            assertEquals(eightInchSingle, testProduct);
        }

        @Test
        @DisplayName("given a non-existent product")
        void getProductBySlugThrowsError() {
            String productSlug = "10-inch-single";
            when(productRepository.findBySlug(productSlug))
                    .thenReturn(Optional.empty());
            assertThrows(
                    ProductDoesNotExistException.class,
                    () -> productServiceImpl.getProductBySlug(productSlug),
                    String.format("Product %s does not exist", productSlug)
            );
        }
    }

    @Nested
    @DisplayName("should update a product")
    class updateProduct {
        // valid product with valid fields
        @Test
        @DisplayName("given valid product and fields")
        void updateProductTest() {
            String slug = "8-inch-single";
            ProductDTO productDTO = new ProductDTO(
                    "8 inch single layer full cake",
                    135.5);
            when(productRepository.findBySlug(slug))
                    .thenReturn(Optional.of(eightInchSingle));
            productServiceImpl.updateProduct(slug, productDTO);
            assertAll(
                    () -> assertEquals("8 inch single layer full cake", eightInchSingle.getName()),
                    () -> assertEquals(135.5F, eightInchSingle.getPrice()),
                    () -> assertNotEquals(120.0F, eightInchSingle.getPrice())

            );
        }

        // valid product with invalid fields
        @Test
        @DisplayName("given valid product with invalid slug")
        void updateProductInvalidSlug() {
            String invalidSlug = "box6";
            ProductDTO b6DTO = new ProductDTO(
                    "box of 6");
            when(productRepository.findBySlug(invalidSlug))
                    .thenReturn(Optional.empty());
            assertThrows(
                    ProductDoesNotExistException.class,
                    () -> productServiceImpl.updateProduct(invalidSlug, b6DTO)
            );
        }

        @Test
        @DisplayName("given valid product with invalid price")
        void updateProductInvalidPrice() {
            String b6Slug = "b6";
            ProductDTO b6DTO = new ProductDTO(0.0);
            when(productRepository.findBySlug(b6Slug))
                    .thenReturn(Optional.of(sixCupcakes));
            productServiceImpl.updateProduct(b6Slug, b6DTO);
            assertAll(
                    () -> assertEquals("Box of 6 Cupcakes", sixCupcakes.getName()),
                    () -> assertNotEquals(0.0F, sixCupcakes.getPrice())
            );
        }

        // non-existent product
        @Test
        @DisplayName("given non-existent product")
        void updateProductInvalid() {
            String slug = "10-inch-single";
            ProductDTO tenDTO = new ProductDTO("10 inch single layer");
            when(productRepository.findBySlug(slug))
                    .thenReturn(Optional.empty());
            assertThrows(
                    ProductDoesNotExistException.class,
                    () -> productServiceImpl.updateProduct(slug, tenDTO)
            );
        }
    }

    @Nested
    @DisplayName("should delete product")
    class deleteProduct {

        @Test
        @DisplayName("given product exists")
        void deleteProductTest() {
            String slug = "b6";
            when(productRepository.findBySlug(slug))
                    .thenReturn(Optional.of(sixCupcakes));
            productServiceImpl.deleteProduct(slug);
            verify(productRepository).delete(sixCupcakes);
        }

        @Test
        @DisplayName("given invalid product")
        void deleteProductDoesNotExist() {
            String slug = "b10";
            when(productRepository.findBySlug(slug))
                    .thenReturn(Optional.empty());
            assertThrows(
                    ProductDoesNotExistException.class,
                    () -> productServiceImpl.deleteProduct(slug)
            );
        }

    }
}
