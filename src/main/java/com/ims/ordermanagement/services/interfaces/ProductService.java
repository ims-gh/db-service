package com.ims.ordermanagement.services.interfaces;

import com.ims.ordermanagement.models.dto.ProductDTO;
import com.ims.ordermanagement.models.entities.Product;

import java.util.List;

public interface ProductService extends DBService {

    List<Product> getAllProducts();

    Product getProductBySlug(String slug);

    List<Product> getProductByCategory(List<String> category);

    void addNewProduct(Product product);

    void updateProduct(String slug, ProductDTO productDTO);

    void deleteProduct(String slug);

}
