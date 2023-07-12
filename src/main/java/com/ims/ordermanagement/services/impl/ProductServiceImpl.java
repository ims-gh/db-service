package com.ims.ordermanagement.services.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.ordermanagement.exceptions.ProductAlreadyExistsException;
import com.ims.ordermanagement.exceptions.ProductDoesNotExistException;
import com.ims.ordermanagement.models.Category;
import com.ims.ordermanagement.models.dto.ProductDTO;
import com.ims.ordermanagement.models.entities.Product;
import com.ims.ordermanagement.repository.ProductRepository;
import com.ims.ordermanagement.services.interfaces.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public List<Product> getProductByCategory(List<String> category) {
        List<String> categories = new ArrayList<>();
        category.forEach(cat -> categories.add(Category.getValue(cat)));
        log.info("Finding product with categories {}", categories);
        return productRepository.findByCategoryIn(categories).orElse(new ArrayList<>());
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
        log.info("Finding product with slug {}", slug);
        Product product = findOrThrowError(slug);
        log.info("Updating product {}", slug);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            mapper.updateValue(product, productDTO);
        } catch (JsonMappingException e) {
            log.error("Exception occurred when updating product with slug {}", slug, e);
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
