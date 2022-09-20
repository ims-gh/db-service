package com.ims.ordermanagement.services.impl;

import com.ims.ordermanagement.exceptions.ProductAlreadyExistsException;
import com.ims.ordermanagement.exceptions.ProductDoesNotExistException;
import com.ims.ordermanagement.models.dto.ProductDTO;
import com.ims.ordermanagement.models.entities.Product;
import com.ims.ordermanagement.repository.ProductRepository;
import com.ims.ordermanagement.services.interfaces.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        log.info("Getting all categories");
        return productRepository.findAll();
    }

    @Override
    public Product getProductBySlug(String slug) {
        log.info("Finding product with Name {}", slug);
        return findOrThrowError(slug);
    }

    @Override
    public void addNewProduct(Product product) {
        log.info("Adding new product: {}", product);
        throwsErrorIfProductExists(product.getSlug());
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProduct(String slug, ProductDTO productDTO) {
        log.info("Extracting product details");
        String name = productDTO.getName();
        Double price = productDTO.getPrice();
        String description = productDTO.getDescription();
        String category = productDTO.getCategory();
        log.info("Finding product with slug {}", slug);
        Product product = findOrThrowError(slug);
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

    @Override
    public void deleteProduct(String slug) {
        log.info("Deleting product {}", slug);
        Product product = findOrThrowError(slug);
        productRepository.delete(product);
    }

    @Override
    public Product findOrThrowError(Object slug) {
        return productRepository.findBySlug((String) slug)
                .orElseThrow(() -> new ProductDoesNotExistException((String) slug));
    }

    private void throwsErrorIfProductExists(String slug) {
        Optional<Product> productOptional = productRepository.findBySlug(slug);
        if (productOptional.isPresent()) {
            throw new ProductAlreadyExistsException(slug);
        }
    }



}
