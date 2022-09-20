package com.ims.ordermanagement.services.interfaces;

import com.ims.ordermanagement.models.dto.ProductDTO;
import com.ims.ordermanagement.models.entities.Product;

import java.util.List;

public interface ProductService extends DBService {

    public List<Product> getAllProducts();

    public Product getProductBySlug(String slug);

    public void addNewProduct(Product product);

    public void updateProduct(String slug, ProductDTO productDTO);

    public void deleteProduct(String slug);

}
