package com.ims.dbservice.services;

import com.ims.dbservice.dto.ProductDTO;
import com.ims.dbservice.exceptions.ProductAlreadyExistsException;
import com.ims.dbservice.exceptions.ProductDoesNotExistException;
import com.ims.dbservice.models.Category;
import com.ims.dbservice.models.Product;
import com.ims.dbservice.repository.ProductRepository;
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
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

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
    @DisplayName("should get all products")
    void getAllProductsTest() {
        List<Product> products = List.of(eightInchSingle, sixCupcakes);
        when(productRepository.findAll())
                .thenReturn(products);

        List<Product> allProducts = productService.getAllProducts();
        assertAll(
                () -> assertEquals(2, allProducts.size()),
                () -> assertTrue(allProducts.get(1).equals(sixCupcakes)),
                () -> assertEquals("FullCake", allProducts.get(0).getCategory())
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
                    Category.FullCake.name()
            );
            productService.addNewProduct(sixInchDouble);
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
                    () -> productService.addNewProduct(eightInchSingle));
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
            Product testProduct = productService.getProductBySlug(productSlug);
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
                    () -> productService.getProductBySlug(productSlug),
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
            productService.updateProduct(slug, productDTO);
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
                    () -> productService.updateProduct(invalidSlug, b6DTO)
            );
        }

        @Test
        @DisplayName("given valid product with invalid price")
        void updateProductInvalidPrice() {
            String b6Slug = "b6";
            ProductDTO b6DTO = new ProductDTO(0.0);
            when(productRepository.findBySlug(b6Slug))
                    .thenReturn(Optional.of(sixCupcakes));
            productService.updateProduct(b6Slug, b6DTO);
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
                    () -> productService.updateProduct(slug, tenDTO)
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
            productService.deleteProduct(slug);
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
                    () -> productService.deleteProduct(slug)
            );
        }

    }
}
