package com.ims.dbservice.services;

import com.ims.dbservice.dto.ProductDTO;
import com.ims.dbservice.exceptions.ProductAlreadyExistsException;
import com.ims.dbservice.exceptions.ProductDoesNotExistException;
import com.ims.dbservice.models.Product;
import com.ims.dbservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        log.info("Getting all categories");
        return productRepository.findAll();
    }

    public Product getProductBySlug(String slug) {
        log.info("Finding product with Name {}", slug);
        return findProductOrThrowError(slug);
    }

    public void addNewProduct(Product product) {
        log.info("Adding new product: {}", product);
        throwsErrorIfProductExists(product.getSlug());
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(String slug, ProductDTO productDTO) {
        log.info("Extracting product details");
        String name = productDTO.getName();
        Double price = productDTO.getPrice();
        String description = productDTO.getDescription();
        String category = productDTO.getCategory();
        log.info("Finding product with slug {}", slug);
        Product product = findProductOrThrowError(slug);
        log.info("Updating product");

        if (isNotNullOrEmptyOrBlank(name) && !name.equals(product.getName())) {
            throwsErrorIfProductExists(name);
            product.setName(name);
            log.info("Product name updated successfully");
        }
        if (isNotNullOrEmptyOrBlank(description) && !description.equals(product.getDescription())) {
            product.setDescription(description);
            log.info("Product description updated successfully");
        }
        if (isNotNullOrEmptyOrBlank(category) && !category.equals(product.getCategory())){
            product.setCategory(category);
            log.info("Product category updated successfully");
        }
        if (!(price <= 0.0) && !price.equals(product.getPrice())){
            product.setPrice(price);
            log.info("Product price updated successfully");
        }
    }

    public void deleteProduct(String slug) {
        log.info("Deleting product {}", slug);
        Product product = findProductOrThrowError(slug);
        productRepository.delete(product);
    }

    private Product findProductOrThrowError(String slug) {
        return productRepository.findBySlug(slug)
                .orElseThrow(() -> new ProductDoesNotExistException(slug));
    }

    private void throwsErrorIfProductExists(String slug) {
        Optional<Product> productOptional = productRepository.findBySlug(slug);
        if (productOptional.isPresent()) {
            throw new ProductAlreadyExistsException(slug);
        }
    }

    private boolean isNotNullOrEmptyOrBlank(String value) {
        return !(value == null || value.isEmpty() || value.isBlank());
    }

}
